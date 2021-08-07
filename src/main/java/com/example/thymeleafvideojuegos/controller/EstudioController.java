package com.example.thymeleafvideojuegos.controller;

import com.example.thymeleafvideojuegos.entity.Estudio;
import com.example.thymeleafvideojuegos.entity.Videojuego;
import com.example.thymeleafvideojuegos.service.ServicioEstudio;
import com.example.thymeleafvideojuegos.service.ServicioGenero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class EstudioController {

    @Autowired
    private ServicioEstudio servicioEstudio;

    @GetMapping("crear/estudio/{id}")
    public String formularioEstudio(Model model, @PathVariable("id") long id) {
        try {
            if(id==0) {
                model.addAttribute("estudio", new Estudio());

            } else {
                model.addAttribute("estudio", this.servicioEstudio.findById(id));
            }
            return "vistas/formularios/formularioEstudio";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }
}
