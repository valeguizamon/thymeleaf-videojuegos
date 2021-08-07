package com.example.thymeleafvideojuegos.controller;

import com.example.thymeleafvideojuegos.entity.Genero;
import com.example.thymeleafvideojuegos.service.ServicioGenero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("crear/genero/{id}")
    public String crearGenero(
            Model model,
            @ModelAttribute("genero") Genero genero,
            @PathVariable("id") Long id
        ) {
        try {
            if(id == 0){
                this.servicioGenero.saveOne(genero);
            }else{
                this.servicioGenero.updateOne(id,genero);
            }
            return "redirect:/admin/abm/genero";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }
}
