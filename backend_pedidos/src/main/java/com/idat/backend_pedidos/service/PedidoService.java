package com.idat.backend_pedidos.service;

import com.idat.backend_pedidos.dto.request.RegistrarPedidoRequest;
import com.idat.backend_pedidos.dto.response.PedidoDetalleResponse;
import com.idat.backend_pedidos.dto.response.PedidoResumenResponse;

import java.util.List;

public interface PedidoService {

    PedidoDetalleResponse registrar(Integer idCliente, RegistrarPedidoRequest request);

    List<PedidoResumenResponse> listarPorCliente(Integer idCliente);

    PedidoDetalleResponse obtenerDetalle(Integer idCliente, Integer idPedido);
}
