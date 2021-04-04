package com.lonner.biblioteca.services;

import com.lonner.biblioteca.entities.Libreria;
import com.lonner.biblioteca.entities.Libro;
import com.lonner.biblioteca.enums.ExistsCheck;
import com.lonner.biblioteca.repositories.LibreriaRepository;
import com.lonner.biblioteca.repositories.LibroRepository;
import com.lonner.biblioteca.types.LibreriaType;
import com.lonner.biblioteca.types.LibroType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibroService {

    private final LibroRepository libroRepository;
    private final LibreriaRepository libreriaRepository;

    private LibroType serializar(Libro libro){
        LibroType libroType = new LibroType();
        BeanUtils.copyProperties(libro,libroType,"libreria");
        return libroType;
    }

    public List<LibroType> buscarPorNombreEdicionyLibreria(String nombre, Integer numeroEdicion, Integer libreriaId){
        Libreria libreria = libreriaRepository.buscarPorId(libreriaId, ExistsCheck.EXCEPTION_IF_NOT_EXISTS);
        return libroRepository.buscarPorLibreriaNombreYEdicion(libreria,nombre,numeroEdicion)
                .stream().map(this::serializar).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LibroType> buscarPorLibreriaId(Integer libreriaId){
        Libreria libreria = libreriaRepository.buscarPorId(libreriaId, ExistsCheck.EXCEPTION_IF_NOT_EXISTS);
        return libreria.getLibros().stream()
                .map(this::serializar)
                .collect(Collectors.toList());
    }


}
