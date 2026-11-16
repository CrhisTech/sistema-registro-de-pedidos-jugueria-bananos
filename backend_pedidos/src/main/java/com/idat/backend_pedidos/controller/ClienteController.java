package com.idat.backend_pedidos.controller;

import com.idat.backend_pedidos.dto.request.ActualizarClienteRequest;
import com.idat.backend_pedidos.dto.request.CambiarContrasenaRequest;
import com.idat.backend_pedidos.dto.response.ClienteResponse;
import com.idat.backend_pedidos.dto.response.MensajeResponse;
import com.idat.backend_pedidos.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes/{idCliente}")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ClienteResponse obtenerPerfil(@PathVariable Integer idCliente) {
        return clienteService.obtenerPerfil(idCliente);
    }

    @PutMapping
    public ClienteResponse actualizarPerfil(@PathVariable Integer idCliente,
                                            @Valid @RequestBody ActualizarClienteRequest request) {
        return clienteService.actualizarPerfil(idCliente, request);
    }

    @PutMapping("/contrasena")
    public MensajeResponse cambiarContrasena(@PathVariable Integer idCliente,
                                             @Valid @RequestBody CambiarContrasenaRequest request) {
        return clienteService.cambiarContrasena(idCliente, request);
    }
}
