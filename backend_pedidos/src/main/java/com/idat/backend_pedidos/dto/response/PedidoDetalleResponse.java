package com.idat.backend_pedidos.dto.response;

import com.idat.backend_pedidos.entity.enums.MetodoPago;
import com.idat.backend_pedidos.entity.enums.Modalidad;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoDetalleResponse(
        Integer idPedido,
        String codigoPedido,
        LocalDateTime fechaRegistro,
        String nombreCliente,
        String celularCliente,
        Modalidad modalidad,
        String sucursal,
        MetodoPago metodoPago,
        String direccionEntrega,
        List<PedidoItemResponse> productos,
        BigDecimal montoTotal
) {
}
