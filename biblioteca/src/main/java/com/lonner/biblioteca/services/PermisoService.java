package com.lonner.biblioteca.services;

import com.lonner.biblioteca.entities.Usuario;
import com.lonner.biblioteca.enums.ExistsCheck;
import com.lonner.biblioteca.exceptions.OperationNotPermittedException;
import com.lonner.biblioteca.repositories.PermisoRepository;
import com.lonner.biblioteca.repositories.UsuarioRepository;
import com.lonner.biblioteca.usuario.enums.Accion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermisoService {

    private final UsuarioRepository usuarioRepository;
    private final PermisoRepository permisoRepository;

    public void validarPermisosUsuarioEnSesion(Accion accion){
        Usuario usuarioSesion = usuarioRepository.buscarUsuarioEnSesion(ExistsCheck.EXCEPTION_IF_NOT_EXISTS);
        boolean usuarioTienePermiso = permisoRepository.comprobarPermisoAccionUsuario(accion, usuarioSesion);
        if(!usuarioTienePermiso){
            throw new OperationNotPermittedException("El usuario: ".concat(usuarioSesion.getNombreUsuario())
            .concat(" no tiene permitido: ").concat(accion.getDescripcion()));
        }
    }
}
