package com.idat.backend_pedidos.dto.response;

import com.idat.backend_pedidos.entity.enums.Modalidad;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PedidoResumenResponse(
        Integer idPedido,
        String codigoPedido,
        LocalDateTime fechaRegistro,
        Modalidad modalidad,
        String sucursal,
        Integer cantidadProductos,
        BigDecimal montoTotal
) {
}
