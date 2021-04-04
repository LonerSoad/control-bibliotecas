package com.lonner.biblioteca.converters;

import com.lonner.biblioteca.usuario.enums.Puesto;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PuestoConverter implements AttributeConverter<Puesto,Integer> {

    @Override
    public Integer convertToDatabaseColumn(Puesto puesto) {
        return puesto.getId();
    }

    @Override
    public Puesto convertToEntityAttribute(Integer puestoId) {
        return Puesto.getPuestoMap().get(puestoId);
    }
}
