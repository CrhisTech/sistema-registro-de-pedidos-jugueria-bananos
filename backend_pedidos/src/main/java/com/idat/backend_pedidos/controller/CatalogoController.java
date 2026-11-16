package com.idat.backend_pedidos.controller;

import com.idat.backend_pedidos.dto.response.CategoriaResponse;
import com.idat.backend_pedidos.dto.response.ProductoResponse;
import com.idat.backend_pedidos.service.CatalogoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalogo")
@RequiredArgsConstructor
public class CatalogoController {

    private final CatalogoService catalogoService;

    @GetMapping("/categorias")
    public List<CategoriaResponse> listarCategorias() {
        return catalogoService.listarCategorias();
    }

    @GetMapping("/productos")
    public List<ProductoResponse> buscarProductos(@RequestParam(required = false) Integer idCategoria,
                                                  @RequestParam(required = false) String buscar) {
        return catalogoService.buscarProductos(idCategoria, buscar);
    }
}
