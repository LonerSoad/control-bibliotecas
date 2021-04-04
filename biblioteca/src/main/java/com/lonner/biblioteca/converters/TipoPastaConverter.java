package com.lonner.biblioteca.converters;

import com.lonner.biblioteca.enums.TipoPasta;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TipoPastaConverter implements AttributeConverter<TipoPasta,Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoPasta attribute) {
        return attribute.getId();
    }

    @Override
    public TipoPasta convertToEntityAttribute(Integer dbData) {
        return TipoPasta.getTipoPastaMap().get(dbData);
    }
}
