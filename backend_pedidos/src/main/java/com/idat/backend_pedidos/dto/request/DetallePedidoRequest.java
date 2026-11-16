package com.idat.backend_pedidos.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record DetallePedidoRequest(
        @NotNull(message = "El producto es obligatorio")
        Integer idProductoOpcion,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad mínima es 1")
        Integer cantidad
) {
}
