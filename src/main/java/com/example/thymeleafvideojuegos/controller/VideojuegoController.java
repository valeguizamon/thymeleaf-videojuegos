package com.example.thymeleafvideojuegos.controller;

import com.example.thymeleafvideojuegos.entity.Videojuego;
import com.example.thymeleafvideojuegos.service.ServicioEstudio;
import com.example.thymeleafvideojuegos.service.ServicioGenero;
import com.example.thymeleafvideojuegos.service.ServicioVideojuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

@Controller
public class VideojuegoController {
    @Autowired
    private ServicioVideojuego servicioVideojuego;
    @Autowired
    private ServicioEstudio servicioEstudio;
    @Autowired
    private ServicioGenero servicioGenero;

    @GetMapping("detalle/videojuego/{id}")
    public String videojuegoDetalle(Model model, @PathVariable Long id){
        try {
            Videojuego videojuego = this.servicioVideojuego.buscarActivoPorId(id);
            model.addAttribute("videojuego",videojuego);
            return "vistas/detalle";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }


    @GetMapping("crear/videojuego/{id}")
    public String formularioVideojuego(Model model, @PathVariable("id") long id) {
        try {
            model.addAttribute("estudios",this.servicioEstudio.findAll());
            model.addAttribute("generos",this.servicioGenero.findAll());
            if(id==0) {
                model.addAttribute("videojuego", new Videojuego());

            } else {
                model.addAttribute("videojuego", this.servicioVideojuego.findById(id));
            }
            return "vistas/formularios/formularioVideojuego";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

    @PostMapping("crear/videojuego/{id}")
    public String crearVideojuego(
            @RequestParam("archivoImagen") MultipartFile archivoImagen,
            Model model,
            @Valid @ModelAttribute("videojuego") Videojuego videojuego,
            BindingResult result,
            @PathVariable("id") Long id
           )throws Exception{
        try {
            model.addAttribute("estudios",this.servicioEstudio.findAll());
            model.addAttribute("generos",this.servicioGenero.findAll());
            if(result.hasErrors()){
                return "vistas/formularios/formularioVideojuego";
            }
            //Ruta Absoluta donde se guardan las imagenes
            String ruta = "C://Videogame//images";
            //Extension del archivo
            String extension = "";
            byte[] bytesImg = archivoImagen.getBytes();

            //ir al ultimo index del nombre de la imagen donde tiene un . (ejemplo = nombre'.png')
            int i = archivoImagen.getOriginalFilename().lastIndexOf('.');
            extension = archivoImagen.getOriginalFilename().substring(i+1);

            //El nombre de la foto se cambia al tiempo en milisegundos (es un número unico)
            Long nombreFoto = Calendar.getInstance().getTimeInMillis();

            // si es distinto de 0 la ruta es la que trae el videojuego ya existente
            //Si el id es 0 la ruta es el nombre de la foto en milisegundos mas la extension,
            Path rutaAbsoluta = id != 0 ?
                    Paths.get(ruta+"//"+videojuego.getImagen()) :
                    Paths.get(ruta+"//"+nombreFoto+"."+extension);
            if(id == 0){
                if(archivoImagen.isEmpty()){
                    model.addAttribute("errorImg","La imagen es requerida");
                    return "vistas/formularios/formularioVideojuego";
                }else{
                    if(!this.validarPeso(archivoImagen)){
                        model.addAttribute("errorImg","El archivo seleccionado tiene que ser menor a 15MB.");
                        return "vistas/formularios/formularioVideojuego";
                    }
                    if(!this.validarExtension(archivoImagen)){
                        model.addAttribute("errorImg","El archivo seleccionado no tiene el formato correcto, formatos aceptados JPG,JPEG,PNG,JFIF.");
                        return "vistas/formularios/formularioVideojuego";
                    }
                }
                //Guardar la imagen en la ruta especificada
                Files.write(rutaAbsoluta,archivoImagen.getBytes());
                //Setear el nombre de la imagen al videojuego
                videojuego.setImagen(nombreFoto+"."+extension);
                this.servicioVideojuego.saveOne(videojuego);
            }else{
                if(!archivoImagen.isEmpty()){
                    if(!this.validarPeso(archivoImagen)){
                        model.addAttribute("errorImg","El archivo seleccionado tiene que ser menor a 15MB");
                        return "vistas/formularios/formularioVideojuego";
                    }
                    if(this.validarExtension(archivoImagen)){
                        //Si la imagen no es vacia y el usuario ya existe se pisa la imagen anterior
                        Files.write(rutaAbsoluta,bytesImg);
                    }else{
                        model.addAttribute("errorImg","El archivo seleccionado no tiene el formato correcto, formatos aceptados JPG,JPEG,PNG,JFIF.");
                        return "vistas/formularios/formularioVideojuego";
                    }

                }
                this.servicioVideojuego.updateOne(id, videojuego);
            }
            return "redirect:/admin/abm/videojuego";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

    @GetMapping("estado/videojuego/{id}")
    public String formularioEstadoVideojuego(Model model, @PathVariable("id") Long id) {
        try {
            model.addAttribute("videojuego", this.servicioVideojuego.findById(id));
            return "vistas/formularios/estado/estadoVideojuego";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

    @PostMapping("estado/videojuego/{id}")
    public String cambiarEstadoVideojuego(Model model, @PathVariable("id") Long id){
        try {
            this.servicioVideojuego.changeState(id);
            return "redirect:/admin/abm/videojuego";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

    //METODOS DE VALIDACION
    /*
     * Devuelve un booleano si un archivo es de tipo imagen.
     * Si no es una imagen tira una excepcion y retorna falso.
     * @param  MultipartFile la imagen a validar en cuestion
     * @return         si es una imagen o no
     */
    private Boolean validarExtension(MultipartFile archivo) throws Exception{
        //Clase Input Stream para leer datos
        try (InputStream input = archivo.getInputStream()) {
            try {
                //Si es una imagen
                ImageIO.read(input).toString();
                return true;
            } catch (Exception e) {
                //Si no es una imagen tira una excepcion
                System.out.println("Error: " + e.getMessage());
                return false;
            }
        } catch (Exception e){
            System.out.println("Error:" + e.getMessage());
            return false;
        }
    }
    /*
     * Devuelve un booleano si el tamaño de un archivo es mayor a 15MB .
     * @param  MultipartFile la imagen a validar en cuestion
     * @return         si es mayor o no
     */
    private Boolean validarPeso(MultipartFile archivo){
        try {
            if(archivo.getSize() <= 14000000){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            System.out.println("Error:" + e.getMessage());
            return false;
        }
    }

    //Referencias
    //Subida de imagenes: https://www.youtube.com/watch?v=BjHEuNdpC-U
    //Validar extension: https://www.it-swarm-es.com/es/java/como-verificar-un-archivo-cargado-si-es-una-imagen-u-otro-archivo/970397053/

}
