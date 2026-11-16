package com.idat.backend_pedidos.dto.response;

import java.math.BigDecimal;

public record PedidoItemResponse(
        String producto,
        String opcion,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal
) {
}
