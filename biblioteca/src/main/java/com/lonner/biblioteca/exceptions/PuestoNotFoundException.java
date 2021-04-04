package com.lonner.biblioteca.exceptions;

public class PuestoNotFoundException extends BusinessException{
    public PuestoNotFoundException(String mensaje,Throwable throwable){
        super(mensaje,throwable);
    }

    public PuestoNotFoundException(String mensaje){
        super(mensaje);
    }
}
