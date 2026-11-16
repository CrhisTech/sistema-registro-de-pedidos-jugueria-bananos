package com.idat.backend_pedidos.entity.converter;

import com.idat.backend_pedidos.entity.enums.Modalidad;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ModalidadConverter implements AttributeConverter<Modalidad, Character> {

    @Override
    public Character convertToDatabaseColumn(Modalidad valor) {
        return valor == null ? null : valor.getCodigo();
    }

    @Override
    public Modalidad convertToEntityAttribute(Character codigo) {
        return codigo == null ? null : Modalidad.desdeCodigo(codigo);
    }
}
