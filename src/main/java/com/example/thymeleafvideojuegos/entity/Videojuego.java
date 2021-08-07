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
public class Videojuego implements Serializable {

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
    private Estudio estudio;

    @JoinColumn(name = "genero", nullable = false, updatable = false)
    @ManyToOne
    private Genero genero;

    @Temporal(TemporalType.DATE)
    private Date fechaLanzamiento;

    public Videojuego() {}
}
