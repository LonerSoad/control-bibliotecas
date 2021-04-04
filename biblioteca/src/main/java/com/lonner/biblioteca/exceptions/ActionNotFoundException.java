package com.lonner.biblioteca.exceptions;

public class ActionNotFoundException extends BusinessException {

    public ActionNotFoundException(String mensaje, Throwable throwable){
        super(mensaje,throwable);
    }

    public ActionNotFoundException(String mensaje){
        super(mensaje);
    }

}
