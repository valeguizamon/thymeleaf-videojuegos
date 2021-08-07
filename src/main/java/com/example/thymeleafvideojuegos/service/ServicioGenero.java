package com.example.thymeleafvideojuegos.service;

import com.example.thymeleafvideojuegos.entity.Genero;
import com.example.thymeleafvideojuegos.repository.RepositorioGenero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioGenero implements ServicioG<Genero>{

    @Autowired
    private RepositorioGenero repositorio;

    @Override
    @Transactional
    public List<Genero> findAll() throws Exception {
        try {
            List<Genero> entities = this.repositorio.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Genero findById(Long id) throws Exception {
        try {
            Optional<Genero> opt = this.repositorio.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Genero saveOne(Genero entity) throws Exception {
        try {
            entity = this.repositorio.save(entity);
            return entity;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Genero updateOne(Long id, Genero genero) throws Exception {
        try {
            Optional<Genero> opt = this.repositorio.findById(id);
            Genero entity = opt.get();
            entity = this.repositorio.save(genero);
            return entity;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean deleteOne(Long id) throws Exception {
        try {
            if (this.repositorio.existsById(id)) {
                this.repositorio.deleteById(id);
            } else {
                throw new Exception();
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
