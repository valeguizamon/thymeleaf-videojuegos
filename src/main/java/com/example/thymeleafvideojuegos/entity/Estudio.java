package com.example.thymeleafvideojuegos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "estudio")
public class Estudio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombre;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "estudio",fetch = FetchType.LAZY)
    private List<Videojuego> videojuegos;

    public Estudio() {
    }
}
