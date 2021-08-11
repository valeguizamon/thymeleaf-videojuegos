package com.example.thymeleafvideojuegos.repository;

import com.example.thymeleafvideojuegos.entity.Videojuego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RepositorioVideojuego extends JpaRepository<Videojuego, Long> {

    @Query(value = "SELECT * FROM videojuegos WHERE videojuegos.titulo LIKE %:q% AND videojuegos.borrado != true", nativeQuery = true)
    List<Videojuego> buscarPorTitulo(@Param("q") String query);

    @Query(value = "SELECT * FROM videojuegos WHERE videojuegos.borrado != true", nativeQuery = true)
    List<Videojuego> buscarActivos();

    @Query("select v from Videojuego v where v.id = ?1 and v.borrado != true")
    Optional<Videojuego> buscarActivoPorId(Long id);

}
