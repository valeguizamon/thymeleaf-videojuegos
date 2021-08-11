package com.example.thymeleafvideojuegos.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

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
@SQLDelete(sql = "UPDATE estudio SET borrado = true WHERE id=?")
@Table(name = "estudio")
public class Estudio implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message="El nombre del estudio no debe estar vácio.")
    @Size(min=2,max=30,message="El nombre del estudio tiene un minimo de 2 caracteres y un maximo de 30 caracteres.")
    private String nombre;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "estudio",fetch = FetchType.LAZY)
    private List<Videojuego> videojuegos;

    private boolean borrado = Boolean.FALSE;

    public Estudio() {
    }
}
