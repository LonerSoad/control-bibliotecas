package com.lonner.biblioteca.services;

import com.lonner.biblioteca.entities.Libreria;
import com.lonner.biblioteca.enums.ExistsCheck;
import com.lonner.biblioteca.repositories.LibreriaRepository;
import com.lonner.biblioteca.types.LibreriaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class LibreriaService {

    private final LibreriaRepository libreriaRepository;

    public LibreriaType buscarPorId(Integer id){
        return serializar(libreriaRepository.buscarPorId(id, ExistsCheck.EXCEPTION_IF_NOT_EXISTS));
    }

    public List<LibreriaType> buscarTodos(){
        return libreriaRepository.buscarTodos(ExistsCheck.EXCEPTION_IF_NOT_EXISTS)
                .stream().map(this::serializar).collect(Collectors.toList());
    }

    private LibreriaType serializar(Libreria libreria){
        LibreriaType libreriaType = new LibreriaType();
        BeanUtils.copyProperties(libreria,libreriaType,"libros");
        return libreriaType;
    }
}
