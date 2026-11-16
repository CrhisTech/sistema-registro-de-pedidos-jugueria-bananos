package com.idat.backend_pedidos.repository;

import com.idat.backend_pedidos.entity.Pedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @EntityGraph(attributePaths = {"sucursal", "detalles"})
    List<Pedido> findByClienteIdClienteOrderByFechaRegistroDesc(Integer idCliente);

    @EntityGraph(attributePaths = {"cliente", "sucursal", "detalles", "detalles.productoOpcion", "detalles.productoOpcion.producto"})
    Optional<Pedido> findByIdPedidoAndClienteIdCliente(Integer idPedido, Integer idCliente);
}
