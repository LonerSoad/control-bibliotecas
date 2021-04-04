package com.lonner.biblioteca.repositories.jpa;

import com.lonner.biblioteca.entities.Usuario;

public interface UsuarioRepositoryI extends CrudRepositoryInterface<Usuario,Integer> {

    public Usuario findByNombreUsuario(String nombreUsuario);
}
