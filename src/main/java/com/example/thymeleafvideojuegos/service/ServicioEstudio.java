package com.example.thymeleafvideojuegos.service;

import com.example.thymeleafvideojuegos.entity.Estudio;
import com.example.thymeleafvideojuegos.entity.Videojuego;
import com.example.thymeleafvideojuegos.repository.RepositorioEstudio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioEstudio implements ServicioG<Estudio> {

    @Autowired
    private RepositorioEstudio repositorio;

    @Override
    @Transactional
    public List<Estudio> findAll() throws Exception {
        try {
            List<Estudio> entities = this.repositorio.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Estudio findById(Long id) throws Exception {
        try {
            Optional<Estudio> opt = this.repositorio.findById(id);
            return opt.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Estudio saveOne(Estudio entity) throws Exception {
        try {
            entity = this.repositorio.save(entity);
            return entity;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Estudio updateOne(Long id, Estudio estudio) throws Exception {
        try {
            Optional<Estudio> opt = this.repositorio.findById(id);
            Estudio entity = opt.get();
            entity = this.repositorio.save(estudio);
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
