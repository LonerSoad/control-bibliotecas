package com.lonner.biblioteca.repositories;

import com.lonner.biblioteca.entities.Libreria;
import com.lonner.biblioteca.enums.ExistsCheck;
import com.lonner.biblioteca.repositories.jpa.LibreriaRepositoryI;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@Log4j2
@RequiredArgsConstructor
public class LibreriaRepository {

    private final LibreriaRepositoryI libreriaRepositoryI;

    public Libreria buscarPorId(Integer id, ExistsCheck existsCheck){
        log.debug("Realizando busqueda de la libreria con id: {}",id);
        Objects.requireNonNull(id,()->"Se debe proporcionar un id para realizar la busqueda");
        return libreriaRepositoryI.validarResultado(libreriaRepositoryI.findById(id).orElse(null),
                existsCheck,null);
    }

    public List<Libreria> buscarTodos(ExistsCheck existsCheck){
        return  libreriaRepositoryI.validarResultado(libreriaRepositoryI.findAll(),existsCheck,()->new ArrayList<>());
    }
}
