package com.lonner.biblioteca.repositories;

import com.lonner.biblioteca.entities.Usuario;
import com.lonner.biblioteca.usuario.enums.Accion;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@Log4j2
public class PermisoRepository {

    private final EntityManager entityManager;

    public boolean comprobarPermisoAccionUsuario(Accion accion, Usuario usuario){
       log.debug("Comprobando permiso para realizar accion: {} por parte del usuario: {}",accion.getDescripcion(),usuario.getNombreUsuario());
        Integer permisoHabilitado =(Integer) entityManager.unwrap(Session.class).createNativeQuery("SELECT 1 FROM PuestoAccion PA WHERE PA.puestoId=:puestoId AND PA.accionId=:accionId")
                .setParameter("puestoId", usuario.getPuesto().getId())
                .setParameter("accionId", accion.getId())
                .uniqueResult();
        return (Objects.nonNull(permisoHabilitado));
    }
}
