package com.example.thymeleafvideojuegos.repository;

import com.example.thymeleafvideojuegos.entity.videojuego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepositorioVideojuego extends JpaRepository<videojuego, Long> {

    @Query(value = "SELECT * FROM videojuegos WHERE videojuegos.titulo LIKE %:q%", nativeQuery = true)
    List<videojuego> buscarPorTitulo(@Param("q") String query);

}
