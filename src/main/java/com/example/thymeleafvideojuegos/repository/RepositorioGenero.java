package com.example.thymeleafvideojuegos.repository;

import com.example.thymeleafvideojuegos.entity.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioGenero extends JpaRepository<Genero,Long> {
}
