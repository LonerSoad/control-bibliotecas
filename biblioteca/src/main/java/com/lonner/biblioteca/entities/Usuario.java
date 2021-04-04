package com.lonner.biblioteca.entities;

import com.lonner.biblioteca.usuario.enums.Puesto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apPaterno;
    private String apMaterno;
    private String nombreUsuario;
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libreriaId")
    private Libreria libreria;
    @Column(name = "puestoId")
    private Puesto puesto;
}

