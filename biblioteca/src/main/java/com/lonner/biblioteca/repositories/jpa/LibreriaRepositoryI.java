package com.lonner.biblioteca.repositories.jpa;

import com.lonner.biblioteca.entities.Libreria;

import java.util.List;

public interface LibreriaRepositoryI extends CrudRepositoryInterface<Libreria,Integer> {
    public List<Libreria> findAll();
}
