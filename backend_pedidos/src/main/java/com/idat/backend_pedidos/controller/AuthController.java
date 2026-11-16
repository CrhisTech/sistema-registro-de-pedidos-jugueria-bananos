package com.idat.backend_pedidos.controller;

import com.idat.backend_pedidos.dto.request.LoginRequest;
import com.idat.backend_pedidos.dto.request.RecuperarContrasenaRequest;
import com.idat.backend_pedidos.dto.request.RegistroClienteRequest;
import com.idat.backend_pedidos.dto.response.ClienteResponse;
import com.idat.backend_pedidos.dto.response.MensajeResponse;
import com.idat.backend_pedidos.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ClienteService clienteService;

    @PostMapping("/registro")
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse registrar(@Valid @RequestBody RegistroClienteRequest request) {
        return clienteService.registrar(request);
    }

    @PostMapping("/login")
    public ClienteResponse iniciarSesion(@Valid @RequestBody LoginRequest request) {
        return clienteService.iniciarSesion(request);
    }

    @PostMapping("/recuperar-contrasena")
    public MensajeResponse recuperarContrasena(@Valid @RequestBody RecuperarContrasenaRequest request) {
        return clienteService.recuperarContrasena(request);
    }
}
