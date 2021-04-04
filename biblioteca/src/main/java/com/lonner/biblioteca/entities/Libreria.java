package com.lonner.biblioteca.entities;

import com.lonner.biblioteca.enums.Estado;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Libreria implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String razonSocial;
    private Estado estado;
    private String calle;
    private int numeroInterior;
    private int numeroExterior;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "libreria")
    private List<Libro> libros;
}
