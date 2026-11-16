package com.idat.backend_pedidos.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Modalidad {
    RECOJO('R'),
    DELIVERY('D');

    private final char codigo;

    public static Modalidad desdeCodigo(char codigo) {
        for (Modalidad valor : values()) {
            if (valor.codigo == codigo) {
                return valor;
            }
        }
        throw new IllegalArgumentException("Modalidad no válida: " + codigo);
    }
}
