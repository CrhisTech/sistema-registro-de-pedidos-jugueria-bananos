package com.idat.backend_pedidos.mapper;

import com.idat.backend_pedidos.dto.response.CategoriaResponse;
import com.idat.backend_pedidos.dto.response.ProductoOpcionResponse;
import com.idat.backend_pedidos.dto.response.ProductoResponse;
import com.idat.backend_pedidos.dto.response.SucursalResponse;
import com.idat.backend_pedidos.entity.Categoria;
import com.idat.backend_pedidos.entity.Producto;
import com.idat.backend_pedidos.entity.ProductoOpcion;
import com.idat.backend_pedidos.entity.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class CatalogoMapper {

    public CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(categoria.getIdCategoria(), categoria.getNombre());
    }

    public SucursalResponse toResponse(Sucursal sucursal) {
        return new SucursalResponse(sucursal.getIdSucursal(), sucursal.getNombre());
    }

    public ProductoOpcionResponse toResponse(ProductoOpcion opcion) {
        return new ProductoOpcionResponse(opcion.getIdProductoOpcion(), opcion.getNombre(), opcion.getPrecio());
    }

    public ProductoResponse toResponse(Producto producto) {
        return new ProductoResponse(
                producto.getIdProducto(),
                producto.getCategoria().getIdCategoria(),
                producto.getCategoria().getNombre(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getUrlImagen(),
                producto.getOpciones().stream().map(this::toResponse).toList()
        );
    }
}
