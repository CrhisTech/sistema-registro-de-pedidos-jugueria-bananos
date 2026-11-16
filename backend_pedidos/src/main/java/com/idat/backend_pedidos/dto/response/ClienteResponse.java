package com.idat.backend_pedidos.dto.response;

public record ClienteResponse(
        Integer idCliente,
        String nombreCompleto,
        String celular,
        String direccion,
        String correo
) {
}
