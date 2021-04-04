package com.lonner.biblioteca.entities;

import com.lonner.biblioteca.enums.TipoPasta;
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
public class Libro implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String autor;
    private String editorial;
    @Column(name = "tipoPastaId")
    private TipoPasta tipoPasta;
    private int numeroEdicion;
    private int numeroPaginas;
    private int ejemplaresDisponibles;
    private double precio;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libreriaId")
    private Libreria libreria;

}
