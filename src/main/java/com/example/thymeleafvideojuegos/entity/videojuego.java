package com.example.thymeleafvideojuegos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "videojuegos")
public class videojuego implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String titulo;

    private String descripcion;

    private String imagen;

    private float precio;

    private boolean oferta;

    private int stock;

    @JoinColumn(name = "estudio", nullable = false, updatable = false)
    @ManyToOne
    private estudio estudio;

    @JoinColumn(name = "genero", nullable = false, updatable = false)
    @ManyToOne
    private genero genero;

    @Temporal(TemporalType.DATE)
    private Date releaseDate;

    public videojuego() {}
}
