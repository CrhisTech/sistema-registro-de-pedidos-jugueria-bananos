package com.idat.backend_pedidos.controller;

import com.idat.backend_pedidos.dto.request.RegistrarPedidoRequest;
import com.idat.backend_pedidos.dto.response.PedidoDetalleResponse;
import com.idat.backend_pedidos.dto.response.PedidoResumenResponse;
import com.idat.backend_pedidos.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/{idCliente}/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDetalleResponse registrar(@PathVariable Integer idCliente,
                                           @Valid @RequestBody RegistrarPedidoRequest request) {
        return pedidoService.registrar(idCliente, request);
    }

    @GetMapping
    public List<PedidoResumenResponse> listar(@PathVariable Integer idCliente) {
        return pedidoService.listarPorCliente(idCliente);
    }

    @GetMapping("/{idPedido}")
    public PedidoDetalleResponse obtenerDetalle(@PathVariable Integer idCliente,
                                                @PathVariable Integer idPedido) {
        return pedidoService.obtenerDetalle(idCliente, idPedido);
    }
}
