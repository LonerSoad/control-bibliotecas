package com.lonner.biblioteca.repositories;

import com.lonner.biblioteca.enums.ExistsCheck;
import com.lonner.biblioteca.exceptions.BusinessException;
import com.lonner.biblioteca.repositories.jpa.CrudRepositoryInterface;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.Collection;
import java.util.function.Supplier;

@Transactional
@Log4j2
public class CrudRepository<T, ID extends Serializable>
        extends SimpleJpaRepository<T, ID>  implements CrudRepositoryInterface<T,ID> {

    private final EntityManager entityManager;

    public CrudRepository(JpaEntityInformation<T, ?> entityInformation, javax.persistence.EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public <S> S validarResultado(S resultado, ExistsCheck existsCheck, Supplier<S> supplier) {
        if (resultado == null || (resultado instanceof Collection && CollectionUtils.isEmpty((Collection) resultado))) {
            switch (existsCheck) {
                case EXCEPTION_IF_NOT_EXISTS:
                    throw new BusinessException("La consulta no obtuvo resultados.");
                case CREATE_IF_NOT_EXIST:
                    if (supplier == null) {
                        throw new BusinessException("Para usar create if not exists debe proporcionar la implementación a utilizar");
                    }
                    resultado = supplier.get();
                    break;
                case AVOID_EXCEPTION:
                    break;
            }
        } else if (existsCheck == ExistsCheck.EXCEPTION_IF_EXISTS) {
            throw new BusinessException("Elemento(s) existente(s) en la base de datos");
        }
        log.debug("Query exitoso sobre el entity " + this.getDomainClass().getName());
        return resultado;
    }

    public void guardar(T t) {
        entityManager.unwrap(Session.class).saveOrUpdate(t);
    }

}
