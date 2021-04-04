package com.lonner.biblioteca.types;

import com.lonner.biblioteca.enums.Estado;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LibreriaType {
    private Integer id;
    private String nombre;
    private String razonSocial;
    private Estado estado;
    private String calle;
    private int numeroInterior;
    private int numeroExterior;
}
