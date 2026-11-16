package com.idat.backend_pedidos.dto.response;

import java.util.List;

public record ProductoResponse(
        Integer idProducto,
        Integer idCategoria,
        String categoria,
        String nombre,
        String descripcion,
        String urlImagen,
        List<ProductoOpcionResponse> opciones
) {
}
