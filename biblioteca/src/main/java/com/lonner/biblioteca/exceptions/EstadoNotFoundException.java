package com.lonner.biblioteca.exceptions;

public class EstadoNotFoundException extends BusinessException{
    public EstadoNotFoundException(String mensaje,Throwable throwable){
        super(mensaje,throwable);
    }
    public EstadoNotFoundException(String mensaje){
        super(mensaje);
    }
}
