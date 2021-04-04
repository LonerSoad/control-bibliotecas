package com.lonner.biblioteca.converters;

import com.lonner.biblioteca.usuario.enums.Accion;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class AccionConverter implements AttributeConverter<Accion,Integer> {
    @Override
    public Integer convertToDatabaseColumn(Accion attribute) {
        return attribute.getId();
    }

    @Override
    public Accion convertToEntityAttribute(Integer dbData) {
        return Accion.getAccionMap().get(dbData);
    }
}
