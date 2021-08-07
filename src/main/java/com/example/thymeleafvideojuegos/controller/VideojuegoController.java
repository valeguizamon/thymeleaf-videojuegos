package com.example.thymeleafvideojuegos.controller;

import com.example.thymeleafvideojuegos.entity.Videojuego;
import com.example.thymeleafvideojuegos.service.ServicioEstudio;
import com.example.thymeleafvideojuegos.service.ServicioGenero;
import com.example.thymeleafvideojuegos.service.ServicioVideojuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            Videojuego videojuego = this.servicioVideojuego.findById(id);
            model.addAttribute("videojuego",videojuego);
            return "vistas/detalle";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }


    @GetMapping("crear/videojuego/{id}")
    public String formularioVideojuegp(Model model, @PathVariable("id") long id) {
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
            Model model,
            @ModelAttribute("videojuego") Videojuego videojuego,
            @RequestParam("archivoImagen") MultipartFile archivoImagen,
            @PathVariable("id") Long id
           ){
        try {
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
                //Guardar la imagen en la ruta especificada
                Files.write(rutaAbsoluta,archivoImagen.getBytes());
                //Setear el nombre de la imagen al videojuego
                videojuego.setImagen(nombreFoto+"."+extension);
                this.servicioVideojuego.saveOne(videojuego);
            }else{
                if(!archivoImagen.isEmpty()){
                    //Si la imagen no es vacia y el usuario ya existe se pisa la imagen anterior
                    Files.write(rutaAbsoluta,bytesImg);
                }
                this.servicioVideojuego.updateOne(id, videojuego);
            }
            return "redirect:/admin/abm/videojuego";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

}
