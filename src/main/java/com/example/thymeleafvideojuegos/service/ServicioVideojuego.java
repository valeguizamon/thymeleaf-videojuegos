package com.example.thymeleafvideojuegos.service;

import com.example.thymeleafvideojuegos.entity.videojuego;
import com.example.thymeleafvideojuegos.repository.RepositorioVideojuego;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class ServicioVideojuego implements ServicioG<videojuego> {

    @Autowired
    private RepositorioVideojuego repositorio;

    @Override
    @Transactional
    public List<videojuego> findAll() throws Exception {
        try {
            List<videojuego> entities = this.repositorio.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public videojuego findById(Long id) throws Exception {
        try {
            Optional<videojuego> opt = this.repositorio.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<videojuego> buscarPorTitulo(String query) throws Exception {
        try {
            List<videojuego> results = this.repositorio.buscarPorTitulo(query);
            return results;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public videojuego saveOne(videojuego entity) throws Exception {
        try {
            entity = this.repositorio.save(entity);
            return entity;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public videojuego updateOne(Long id, videojuego juego) throws Exception {
        try {
            Optional<videojuego> opt = this.repositorio.findById(id);
            videojuego entity = opt.get();
            entity = this.repositorio.save(juego);
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