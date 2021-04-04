package com.lonner.biblioteca.exceptions;

public class BookNotFoundException extends BusinessException{

    public BookNotFoundException(String mensaje,Throwable throwable){
        super(mensaje,throwable);
    }

    public BookNotFoundException(String mensaje){
        super(mensaje);
    }

}
