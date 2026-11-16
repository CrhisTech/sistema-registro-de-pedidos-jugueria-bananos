package com.idat.backend_pedidos.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MetodoPago {
    EFECTIVO('E'),
    TARJETA('T'),
    BILLETERA_DIGITAL('B');

    private final char codigo;

    public static MetodoPago desdeCodigo(char codigo) {
        for (MetodoPago valor : values()) {
            if (valor.codigo == codigo) {
                return valor;
            }
        }
        throw new IllegalArgumentException("Método de pago no válido: " + codigo);
    }
}
