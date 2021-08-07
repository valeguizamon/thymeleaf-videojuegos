package com.example.thymeleafvideojuegos.controller;

import com.example.thymeleafvideojuegos.entity.Videojuego;
import com.example.thymeleafvideojuegos.service.ServicioEstudio;
import com.example.thymeleafvideojuegos.service.ServicioGenero;
import com.example.thymeleafvideojuegos.service.ServicioVideojuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

}
