package com.idat.backend_pedidos.dto.response;

import java.math.BigDecimal;

public record ProductoOpcionResponse(
        Integer idProductoOpcion,
        String nombre,
        BigDecimal precio
) {
}
