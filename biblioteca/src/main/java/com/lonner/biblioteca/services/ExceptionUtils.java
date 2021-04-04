package com.lonner.biblioteca.services;

import com.lonner.biblioteca.exceptions.BusinessException;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

@Log4j2
public class ExceptionUtils {

    public ExceptionUtils() {
    }

    public static String extraerMensaje(Exception ex) {
        return extraerMensaje(ex, false);
    }

    public static String extraerMensaje(Exception ex, boolean traeNombreClase, Class... clasesConocidas) {
        String mensajes;
        try {
            Throwable th = ex;

            ArrayList mensajesList;
            for(mensajesList = new ArrayList(); th != null; th = ((Throwable)th).getCause()) {
                String mensaje = ((Throwable)th).getMessage();
                Class clase = th.getClass();
                if (!(th instanceof NullPointerException)) {
                     if (!StringUtils.isEmpty(mensaje) && (th instanceof BusinessException || th instanceof InternalError || clasesConocidas != null && Arrays.asList(clasesConocidas).stream().anyMatch((claseConocida) -> {
                        return claseConocida.isAssignableFrom(clase);
                    }))) {
                        if (traeNombreClase) {
                            mensaje = mensaje.substring(mensaje.indexOf(": ") + 1);
                        }

                        mensajesList.add(mensaje);
                    }
                }
            }

            if (mensajesList.isEmpty()) {
                mensajes = "Error interno del servidor";
            } else {
                mensajes = mensajesList.stream().collect(Collectors.joining(" . ")).toString();
            }
        } catch (Exception var8) {
            mensajes = "Error al leer la excepción" + var8.getMessage() != null ? " " + var8.getMessage() : "";
        }

        log.warn( mensajes, ex);
        return mensajes;
    }


}
