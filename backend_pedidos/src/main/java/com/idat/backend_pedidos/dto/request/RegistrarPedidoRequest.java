package com.idat.backend_pedidos.dto.request;

import com.idat.backend_pedidos.entity.enums.MetodoPago;
import com.idat.backend_pedidos.entity.enums.Modalidad;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RegistrarPedidoRequest(
        @NotNull(message = "La sucursal de atención es obligatoria")
        Integer idSucursal,

        @NotNull(message = "La modalidad es obligatoria (RECOJO o DELIVERY)")
        Modalidad modalidad,

        @NotNull(message = "El método de pago es obligatorio (EFECTIVO, TARJETA o BILLETERA_DIGITAL)")
        MetodoPago metodoPago,

        @NotEmpty(message = "El pedido debe tener al menos un producto")
        List<@Valid @NotNull DetallePedidoRequest> detalles
) {
}
