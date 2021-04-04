package com.lonner.biblioteca.apis;

import com.lonner.biblioteca.services.LibroService;
import com.lonner.biblioteca.services.PermisoService;
import com.lonner.biblioteca.types.LibroType;
import com.lonner.biblioteca.usuario.enums.Accion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LibrosApi extends AbstractApi{

    private final LibroService libroService;
    private final PermisoService permisoService;

    @GetMapping("/libros/filtrar")
    @ResponseStatus(HttpStatus.OK)
    public List<LibroType> busquedaLibrosParametros(@RequestParam(required = false) String nombre, @RequestParam(required = false) Integer numeroEdicion,
                                                        @RequestParam Integer libreriaId){
        permisoService.validarPermisosUsuarioEnSesion(Accion.CONSULTAR);
        return  libroService.buscarPorNombreEdicionyLibreria(nombre,numeroEdicion,libreriaId);
    }

    @GetMapping("/libros")
    @ResponseStatus(HttpStatus.OK)
    public List<LibroType> librosEnLibreria(@RequestParam Integer libreriaId){
        permisoService.validarPermisosUsuarioEnSesion(Accion.CONSULTAR);
        return libroService.buscarPorLibreriaId(libreriaId);
    }
}
