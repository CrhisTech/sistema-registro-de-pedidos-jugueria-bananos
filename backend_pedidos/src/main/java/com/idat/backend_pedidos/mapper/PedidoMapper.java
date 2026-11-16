package com.idat.backend_pedidos.mapper;

import com.idat.backend_pedidos.dto.response.PedidoDetalleResponse;
import com.idat.backend_pedidos.dto.response.PedidoItemResponse;
import com.idat.backend_pedidos.dto.response.PedidoResumenResponse;
import com.idat.backend_pedidos.entity.Pedido;
import com.idat.backend_pedidos.entity.PedidoDetalle;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public PedidoResumenResponse toResumen(Pedido pedido) {
        int cantidadProductos = pedido.getDetalles().stream()
                .mapToInt(PedidoDetalle::getCantidad)
                .sum();
        return new PedidoResumenResponse(
                pedido.getIdPedido(),
                pedido.getCodigoPedido(),
                pedido.getFechaRegistro(),
                pedido.getModalidad(),
                pedido.getSucursal().getNombre(),
                cantidadProductos,
                pedido.getMontoTotal()
        );
    }

    public PedidoDetalleResponse toDetalle(Pedido pedido) {
        return new PedidoDetalleResponse(
                pedido.getIdPedido(),
                pedido.getCodigoPedido(),
                pedido.getFechaRegistro(),
                pedido.getCliente().getNombreCompleto(),
                pedido.getCliente().getCelular(),
                pedido.getModalidad(),
                pedido.getSucursal().getNombre(),
                pedido.getMetodoPago(),
                pedido.getDireccionEntrega(),
                pedido.getDetalles().stream().map(this::toItem).toList(),
                pedido.getMontoTotal()
        );
    }

    private PedidoItemResponse toItem(PedidoDetalle detalle) {
        return new PedidoItemResponse(
                detalle.getProductoOpcion().getProducto().getNombre(),
                detalle.getProductoOpcion().getNombre(),
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                detalle.getSubtotal()
        );
    }
}
