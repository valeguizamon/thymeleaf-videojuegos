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

    @GetMapping("activar/genero/{id}")
    public String formularioActivarGenero(Model model, @PathVariable("id") Long id) {
        try {
            model.addAttribute("genero", this.servicioGenero.findById(id));
            return "vistas/formularios/activar/activarGenero";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

    @PostMapping("activar/genero/{id}")
    public String activarGenero(Model model, @PathVariable("id") Long id){
        try {
            Genero genero = this.servicioGenero.findById(id);
            genero.setBorrado(false);
            this.servicioGenero.updateOne(id,genero);
            return "redirect:/admin/abm/genero";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

    @GetMapping("eliminar/genero/{id}")
    public String formularioEliminarGenero(Model model, @PathVariable("id") Long id) {
        try {
            model.addAttribute("genero", this.servicioGenero.findById(id));
            return "vistas/formularios/eliminar/eliminarGenero";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

    @PostMapping("eliminar/genero/{id}")
    public String eliminarGenero(Model model, @PathVariable("id") Long id){
        try {
            this.servicioGenero.deleteOne(id);
            return "redirect:/admin/abm/genero";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }
}
