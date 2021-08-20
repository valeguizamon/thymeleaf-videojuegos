package com.example.thymeleafvideojuegos.controller;

import com.example.thymeleafvideojuegos.entity.Estudio;
import com.example.thymeleafvideojuegos.entity.Genero;
import com.example.thymeleafvideojuegos.entity.Videojuego;
import com.example.thymeleafvideojuegos.service.ServicioGenero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Date;

@Controller
public class GeneroController {

    @Autowired
    private ServicioGenero servicioGenero;

    @GetMapping("crear/genero/{id}")
    public String formularioGenero(Model model, @PathVariable("id") Long id) {
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
            @Valid @ModelAttribute("genero") Genero genero,
            BindingResult result,
            @PathVariable("id") Long id
        ) {
        try {
            if(result.hasErrors()){
                return "vistas/formularios/formularioGenero";
            }
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

    @GetMapping("estado/genero/{id}")
    public String formularioEstadoGenero(Model model, @PathVariable("id") Long id) {
        try {
            model.addAttribute("genero", this.servicioGenero.findById(id));
            return "vistas/formularios/estado/estadoGenero";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

    @PostMapping("estado/genero/{id}")
    public String cambiarEstadoGenero(Model model, @PathVariable("id") Long id){
        try {
            this.servicioGenero.changeState(id);
            return "redirect:/admin/abm/genero";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }
}
