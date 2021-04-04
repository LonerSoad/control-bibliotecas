package com.lonner.biblioteca.repositories;

import com.lonner.biblioteca.entities.Usuario;
import com.lonner.biblioteca.enums.ExistsCheck;
import com.lonner.biblioteca.repositories.jpa.UsuarioRepositoryI;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
@Log4j2
@RequiredArgsConstructor
public class UsuarioRepository {

    private final UsuarioRepositoryI usuarioRepositoryI;

    private String buscarNombreUsuarioEnSesion() {
        String username = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (Objects.nonNull(securityContext)) {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication != null) {
                username = authentication.getName();
            }
        }
        return username;
    }
    public Usuario buscarPorNombreUsuario(String nombreUsuario, ExistsCheck existsCheck){
        log.info("Buscando usuario usando el atributo nombreUsuario");
        Objects.requireNonNull(nombreUsuario,()->"Se debe proporcionar el nombre de usuario para realizar la busqueda." );
        return usuarioRepositoryI.validarResultado(usuarioRepositoryI.findByNombreUsuario(nombreUsuario)
                ,existsCheck,()->null);
    }

    public Usuario buscarUsuarioEnSesion(ExistsCheck existsCheck) {
        log.info("Buscando usuario en sesión");
        String username;
        username = buscarNombreUsuarioEnSesion();
        return buscarPorNombreUsuario(username,existsCheck);
    }


}
