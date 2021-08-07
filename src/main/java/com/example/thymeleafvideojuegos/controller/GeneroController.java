package com.example.thymeleafvideojuegos.controller;

import com.example.thymeleafvideojuegos.entity.Genero;
import com.example.thymeleafvideojuegos.service.ServicioGenero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GeneroController {

    @Autowired
    private ServicioGenero servicioGenero;

    @GetMapping("crear/genero/{id}")
    public String formularioGenero(Model model, @PathVariable("id") long id) {
        try {
            if(id==0) {
                model.addAttribute("genero", new Genero());

            } else {
                model.addAttribute("genero", this.servicioGenero.findById(id));
            }
            return "vistas/formularios/formularioGenero";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }
}
