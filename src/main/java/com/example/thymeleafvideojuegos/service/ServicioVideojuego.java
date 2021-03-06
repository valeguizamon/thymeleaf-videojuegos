package com.example.thymeleafvideojuegos.service;

import com.example.thymeleafvideojuegos.entity.Videojuego;
import com.example.thymeleafvideojuegos.repository.RepositorioVideojuego;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioVideojuego implements ServicioG<Videojuego> {

    @Autowired
    private RepositorioVideojuego repositorio;

    @Override
    @Transactional
    public List<Videojuego> findAll() throws Exception {
        try {
            List<Videojuego> entities = this.repositorio.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Videojuego> finadAllActive() throws Exception {
        try {
            List<Videojuego> entities = this.repositorio.buscarActivos();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Videojuego findById(Long id) throws Exception {
        try {
            Optional<Videojuego> opt = this.repositorio.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public Videojuego buscarActivoPorId(Long id) throws Exception {
        try {
            Optional<Videojuego> videojuegoOpt = this.repositorio.buscarActivoPorId(id);
            if(videojuegoOpt.isEmpty()){
                throw new Exception();
            }
            return videojuegoOpt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public List<Videojuego> buscarPorTitulo(String query) throws Exception {
        try {
            List<Videojuego> results = this.repositorio.buscarPorTitulo(query);
            return results;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Videojuego saveOne(Videojuego entity) throws Exception {
        try {
            entity = this.repositorio.save(entity);
            return entity;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Videojuego updateOne(Long id, Videojuego juego) throws Exception {
        try {
            Optional<Videojuego> opt = this.repositorio.findById(id);
            Videojuego entity = opt.get();
            entity = this.repositorio.save(juego);
            return entity;
        } catch (Exception e) {
            System.out.println(e);
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean changeState(Long id) throws Exception {
        try {
            Optional<Videojuego> opt = this.repositorio.findById(id);
            if (!opt.isEmpty()) {
                Videojuego videojuego = opt.get();
                videojuego.setBorrado(!videojuego.isBorrado());
                this.repositorio.save(videojuego);
            } else {
                throw new Exception();
            }
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}