package com.example.thymeleafvideojuegos.controller;

import com.example.thymeleafvideojuegos.entity.Videojuego;
import com.example.thymeleafvideojuegos.service.ServicioEstudio;
import com.example.thymeleafvideojuegos.service.ServicioGenero;
import com.example.thymeleafvideojuegos.service.ServicioVideojuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class MainController {

    @Autowired
    private ServicioVideojuego servicioVideojuego;
    @Autowired
    private ServicioEstudio servicioEstudio;
    @Autowired
    private ServicioGenero servicioGenero;


    @RequestMapping(value="/", method = RequestMethod.GET)
    //Retornar el string del nombre del template a llamar
    public String index(Model model){
        try {
            //ArrayList<Videojuego> videojuegos = (ArrayList<Videojuego>) this.servicioVideojuego.findAll();
            ArrayList<Videojuego> videojuegos = (ArrayList<Videojuego>) this.servicioVideojuego.finadAllActive();
            model.addAttribute("videojuegos",videojuegos);
            return "index";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

    @GetMapping("carrito")
    //Carrito
    public String carrito(Model model) {
        try {
            return "vistas/carrito";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

    @GetMapping("busqueda")
    public String busquedaVideojuego(Model model, @RequestParam(value = "query", required = false) String q){
        try {
            if(q!=null) {
                ArrayList<Videojuego> resultados = (ArrayList<Videojuego>) this.servicioVideojuego.buscarPorTitulo(q);
                model.addAttribute("resultados",resultados);
            }
            return "vistas/busqueda";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

    //Rutas ADMIN
    @GetMapping("admin")
    public String adminVista(Model model){
        try {
            return "vistas/admin";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }
    @GetMapping("admin/abm/videojuego")
    public String abmVideojuego(Model model){
        try {
            model.addAttribute("videojuegos",this.servicioVideojuego.findAll());
            return "vistas/abm/videojuego";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

    @GetMapping("admin/abm/estudio")
    public String abmEstudio(Model model){
        try {
            model.addAttribute("estudios",this.servicioEstudio.findAll());
            return "vistas/abm/estudio";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

    @GetMapping("admin/abm/genero")
    public String abmGenero(Model model){
        try {
            model.addAttribute("generos",this.servicioGenero.findAll());
            return "vistas/abm/genero";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

}
