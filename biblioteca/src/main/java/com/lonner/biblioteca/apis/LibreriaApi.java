package com.lonner.biblioteca.apis;

import com.lonner.biblioteca.services.LibreriaService;
import com.lonner.biblioteca.services.PermisoService;
import com.lonner.biblioteca.types.LibreriaType;
import com.lonner.biblioteca.usuario.enums.Accion;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LibreriaApi extends AbstractApi {

    private final LibreriaService libreriaService;
    private final PermisoService permisoService;

    @GetMapping("/libreria/todas")
    @ResponseStatus(HttpStatus.OK)
    public List<LibreriaType> libreriasRegistradas(){
        permisoService.validarPermisosUsuarioEnSesion(Accion.CONSULTAR);
        return libreriaService.buscarTodos();
    }

    @GetMapping("/libreria/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LibreriaType libreriaPorId(@PathVariable Integer id){
        permisoService.validarPermisosUsuarioEnSesion(Accion.CONSULTAR);
        return libreriaService.buscarPorId(id);
    }
}
