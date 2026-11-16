package com.idat.backend_pedidos.entity.converter;

import com.idat.backend_pedidos.entity.enums.MetodoPago;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MetodoPagoConverter implements AttributeConverter<MetodoPago, Character> {

    @Override
    public Character convertToDatabaseColumn(MetodoPago valor) {
        return valor == null ? null : valor.getCodigo();
    }

    @Override
    public MetodoPago convertToEntityAttribute(Character codigo) {
        return codigo == null ? null : MetodoPago.desdeCodigo(codigo);
    }
}
