package com.example.thymeleafvideojuegos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
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

    @NotEmpty
    @Size(min=2,max=30,message="El nombre del estudio tiene un minimo de 2 caracteres y un maximo de 30 caracteres")
    private String nombre;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "estudio",fetch = FetchType.LAZY)
    private List<Videojuego> videojuegos;

    public Estudio() {
    }
}
