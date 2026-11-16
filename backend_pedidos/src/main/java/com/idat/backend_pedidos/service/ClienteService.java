package com.idat.backend_pedidos.service;

import com.idat.backend_pedidos.dto.request.ActualizarClienteRequest;
import com.idat.backend_pedidos.dto.request.CambiarContrasenaRequest;
import com.idat.backend_pedidos.dto.request.LoginRequest;
import com.idat.backend_pedidos.dto.request.RecuperarContrasenaRequest;
import com.idat.backend_pedidos.dto.request.RegistroClienteRequest;
import com.idat.backend_pedidos.dto.response.ClienteResponse;
import com.idat.backend_pedidos.dto.response.MensajeResponse;

public interface ClienteService {

    ClienteResponse registrar(RegistroClienteRequest request);

    ClienteResponse iniciarSesion(LoginRequest request);

    MensajeResponse recuperarContrasena(RecuperarContrasenaRequest request);

    ClienteResponse obtenerPerfil(Integer idCliente);

    ClienteResponse actualizarPerfil(Integer idCliente, ActualizarClienteRequest request);

    MensajeResponse cambiarContrasena(Integer idCliente, CambiarContrasenaRequest request);
}
