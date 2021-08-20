package com.example.thymeleafvideojuegos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "videojuegos")
public class Videojuego implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "{NotEmpty.Videojuego.titulo}")
    @Size(min = 2, max = 30, message = "{Size.Videojuego.titulo}")
    private String titulo;

    @NotEmpty(message = "{NotEmpty.Videojuego.descripcion}")
    @Size(min = 25, max = 100, message = "{Size.Videojuego.descripcion}")
    private String descripcion;

    private String imagen;

    @NotNull(message = "{NotNull.Videojuego.precio}")
    @Min(value=1, message = "{Min.Videojuego.precio}")
    @Max(value=10000, message = "{Min.Videojuego.precio}")
    private float precio;

    @NotNull(message = "{NotNull.Videojuego.oferta}")
    private boolean oferta;

    @NotNull(message = "{NotNull.Videojuego.stock}")
    @Min(value=1, message = "{Min.Videojuego.stock}")
    @Max(value=10000 , message = "{Min.Videojuego.stock}")
    private int stock;

    @NotNull(message = "{NotNull.Videojuego.estudio}")
    @JoinColumn(name = "estudio", nullable = false, updatable = false)
    @ManyToOne
    private Estudio estudio;

    @NotNull(message = "{NotNull.Videojuego.genero}")
    @JoinColumn(name = "genero", nullable = false, updatable = false)
    @ManyToOne
    private Genero genero;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "{NotNull.Videojuego.fechaLanzamiento}")
    @PastOrPresent(message = "{PastOrPresent.Videojuego.fechaLanzamiento}")
    private Date fechaLanzamiento;

    private boolean borrado = Boolean.FALSE;

    public Videojuego() {}
}
