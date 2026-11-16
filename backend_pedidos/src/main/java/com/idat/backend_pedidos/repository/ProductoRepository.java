package com.idat.backend_pedidos.repository;

import com.idat.backend_pedidos.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    @Query("""
            SELECT DISTINCT p
            FROM Producto p
            JOIN FETCH p.categoria c
            JOIN FETCH p.opciones o
            WHERE (:idCategoria IS NULL OR c.idCategoria = :idCategoria)
              AND (:texto IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :texto, '%')))
            ORDER BY c.orden, p.nombre
            """)
    List<Producto> buscarCatalogo(@Param("idCategoria") Integer idCategoria,
                                  @Param("texto") String texto);
}
