package com.example.thymeleafvideojuegos.service;

import java.util.List;

public interface ServicioG <T>{

    public List<T> findAll() throws Exception;

    public T findById(Long id) throws Exception;

    public T saveOne(T entity) throws Exception;

    public T updateOne(Long id, T entity) throws Exception;

    public boolean changeState(Long id) throws Exception;

}
