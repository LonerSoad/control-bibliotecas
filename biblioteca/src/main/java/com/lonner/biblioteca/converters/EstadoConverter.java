package com.lonner.biblioteca.converters;

import com.lonner.biblioteca.enums.Estado;

import javax.persistence.AttributeConverter;

public class EstadoConverter implements AttributeConverter<Estado,Integer> {
    @Override
    public Integer convertToDatabaseColumn(Estado attribute) {
        return attribute.getId();
    }

    @Override
    public Estado convertToEntityAttribute(Integer dbData) {
        return Estado.getEstadoMap().get(dbData);
    }
}
