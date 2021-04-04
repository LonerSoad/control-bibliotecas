package com.lonner.biblioteca.apis;

import com.lonner.biblioteca.services.ExceptionUtils;
import com.lonner.biblioteca.types.MensajeExcepcionRest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

public class AbstractApi {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MensajeExcepcionRest handleAppException(Exception ex) {
        return new MensajeExcepcionRest(ExceptionUtils.extraerMensaje(ex));
    }
}
