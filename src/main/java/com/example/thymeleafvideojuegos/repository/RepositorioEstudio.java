package com.example.thymeleafvideojuegos.repository;

import com.example.thymeleafvideojuegos.entity.Estudio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioEstudio extends JpaRepository<Estudio,Long> {
}
