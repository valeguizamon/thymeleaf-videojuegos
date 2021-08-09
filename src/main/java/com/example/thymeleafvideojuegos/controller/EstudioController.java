package com.example.thymeleafvideojuegos.controller;

import com.example.thymeleafvideojuegos.entity.Estudio;
import com.example.thymeleafvideojuegos.entity.Videojuego;
import com.example.thymeleafvideojuegos.service.ServicioEstudio;
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
public class EstudioController {

    @Autowired
    private ServicioEstudio servicioEstudio;

    @GetMapping("crear/estudio/{id}")
    public String formularioEstudio(Model model, @PathVariable("id") Long id) {
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

    @PostMapping("crear/estudio/{id}")
    public String crearEstudio(
            Model model,
            @Valid @ModelAttribute("estudio") Estudio estudio,
            BindingResult result,
            @PathVariable("id") Long id
    ) {
        try {
            if(result.hasErrors()){
                return "vistas/formularios/formularioEstudio";
            }
            if(id == 0){
                this.servicioEstudio.saveOne(estudio);
            }else{
                this.servicioEstudio.updateOne(id,estudio);
            }
            return "redirect:/admin/abm/estudio";
        }catch(Exception e){
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

    @GetMapping("eliminar/estudio/{id}")
    public String formularioEliminarEstudio(Model model, @PathVariable("id") Long id) {
        try {
            model.addAttribute("estudio", this.servicioEstudio.findById(id));
            return "vistas/formularios/eliminar/eliminarEstudio";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }

    @PostMapping("eliminar/estudio/{id}")
    public String eliminarEstudio(Model model, @PathVariable("id") Long id){
        try {
            this.servicioEstudio.deleteOne(id);
            return "redirect:/admin/abm/estudio";
        } catch (Exception e) {
            model.addAttribute("error",e.getMessage());
            return "vistas/error";
        }
    }
}
