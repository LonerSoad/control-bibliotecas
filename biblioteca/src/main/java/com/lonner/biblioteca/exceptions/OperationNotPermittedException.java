package com.lonner.biblioteca.exceptions;

public class OperationNotPermittedException  extends BusinessException{
    public OperationNotPermittedException(String mensaje,Throwable throwable){
        super(mensaje,throwable);
    }

    public OperationNotPermittedException(String mensaje){
        super(mensaje);
    }
}
