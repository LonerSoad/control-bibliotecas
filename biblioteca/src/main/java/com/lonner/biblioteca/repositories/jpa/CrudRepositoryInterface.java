package com.lonner.biblioteca.repositories.jpa;

import com.lonner.biblioteca.enums.ExistsCheck;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import java.io.Serializable;
import java.util.Optional;
import java.util.function.Supplier;

@NoRepositoryBean
public interface CrudRepositoryInterface<T, ID extends Serializable>  extends Repository<T, ID> {

    public void guardar(T t);
    public Optional<T> findById(ID id);
    public <S> S validarResultado(S resultado, ExistsCheck existsCheck, Supplier<S> supplier);
}
