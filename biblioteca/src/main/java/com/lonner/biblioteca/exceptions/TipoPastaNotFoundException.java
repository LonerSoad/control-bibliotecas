package com.lonner.biblioteca.exceptions;

public class TipoPastaNotFoundException extends BusinessException{
    public TipoPastaNotFoundException(String mensaje,Throwable throwable){
        super(mensaje,throwable);
    }

    public TipoPastaNotFoundException(String mensaje){
        super(mensaje);
    }
}
