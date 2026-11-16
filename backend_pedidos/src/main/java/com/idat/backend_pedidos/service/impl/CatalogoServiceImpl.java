package com.idat.backend_pedidos.service.impl;

import com.idat.backend_pedidos.dto.response.CategoriaResponse;
import com.idat.backend_pedidos.dto.response.ProductoResponse;
import com.idat.backend_pedidos.mapper.CatalogoMapper;
import com.idat.backend_pedidos.repository.CategoriaRepository;
import com.idat.backend_pedidos.repository.ProductoRepository;
import com.idat.backend_pedidos.service.CatalogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogoServiceImpl implements CatalogoService {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final CatalogoMapper catalogoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponse> listarCategorias() {
        return categoriaRepository.findAllByOrderByOrdenAsc().stream()
                .map(catalogoMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> buscarProductos(Integer idCategoria, String texto) {
        String filtro = (texto == null || texto.isBlank()) ? null : texto.trim();
        return productoRepository.buscarCatalogo(idCategoria, filtro).stream()
                .map(catalogoMapper::toResponse)
                .toList();
    }
}
