package com.example.thymeleafvideojuegos.repository;

import com.example.thymeleafvideojuegos.entity.videojuego;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioVideojuego extends JpaRepository<videojuego, Long> {

}
