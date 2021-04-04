package com.lonner.biblioteca.types;

import com.lonner.biblioteca.entities.Libreria;
import com.lonner.biblioteca.enums.TipoPasta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibroType {

    private Integer id;
    private String nombre;
    private String autor;
    private String editorial;
    private TipoPasta tipoPasta;
    private int numeroEdicion;
    private int numeroPaginas;
    private int ejemplaresDisponibles;
    private double precio;
}
