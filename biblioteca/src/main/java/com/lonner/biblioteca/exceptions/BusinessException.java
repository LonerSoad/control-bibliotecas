package com.lonner.biblioteca.exceptions;

public class BusinessException extends RuntimeException{
    public BusinessException(String mensaje,Throwable throwable){
        super(mensaje,throwable);
    }

    public BusinessException(String mensaje){
        super(mensaje);
    }
}
