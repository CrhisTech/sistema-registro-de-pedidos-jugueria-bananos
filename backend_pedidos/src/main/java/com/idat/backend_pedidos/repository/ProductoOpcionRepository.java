package com.idat.backend_pedidos.repository;

import com.idat.backend_pedidos.entity.ProductoOpcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ProductoOpcionRepository extends JpaRepository<ProductoOpcion, Integer> {

    @Query("""
            SELECT o
            FROM ProductoOpcion o
            JOIN FETCH o.producto p
            WHERE o.idProductoOpcion IN :ids
            """)
    List<ProductoOpcion> buscarPorIds(@Param("ids") Collection<Integer> ids);
}
