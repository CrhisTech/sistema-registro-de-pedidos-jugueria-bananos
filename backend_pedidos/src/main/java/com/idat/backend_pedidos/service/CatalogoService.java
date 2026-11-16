package com.idat.backend_pedidos.service;

import com.idat.backend_pedidos.dto.response.CategoriaResponse;
import com.idat.backend_pedidos.dto.response.ProductoResponse;

import java.util.List;

public interface CatalogoService {

    List<CategoriaResponse> listarCategorias();

    List<ProductoResponse> buscarProductos(Integer idCategoria, String texto);
}
