package com.lonner.biblioteca.repositories;

import com.lonner.biblioteca.entities.Libreria;
import com.lonner.biblioteca.entities.Libro;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
@Log4j2
public class LibroRepository {

    private final EntityManager entityManager;


    public List<Libro> buscarPorLibreriaNombreYEdicion(Libreria libreria,String nombre,Integer numeroEdicion){
        log.info("Se buscaran los libros con nombre: {} , numero de edicion: {} y libreria: {}",nombre,numeroEdicion,libreria.getNombre());
        Objects.requireNonNull(libreria,()-> "Se debe proporcionar la libreria donde realizar la busqueda");
        CriteriaBuilder criteriaBuilder = entityManager.unwrap(Session.class).getCriteriaBuilder();
        CriteriaQuery<Libro> libroCriteria = criteriaBuilder.createQuery(Libro.class);
        Root<Libro> libro = libroCriteria.from(Libro.class);
        Predicate predicadoBusqueda = criteriaBuilder.and(criteriaBuilder.equal(libro.get("libreria"), libreria));
        if(Objects.nonNull(nombre)){
            predicadoBusqueda = criteriaBuilder.and(predicadoBusqueda,criteriaBuilder.equal(libro.get("nombre"),nombre));
        }
        if(Objects.nonNull(numeroEdicion)){
            predicadoBusqueda = criteriaBuilder.and(predicadoBusqueda,criteriaBuilder.equal(libro.get("numeroEdicion"),numeroEdicion));
        }
        libroCriteria.select(libro).where(predicadoBusqueda);
        return entityManager.unwrap(Session.class).createQuery(libroCriteria).list();

    }
}
