package com.idat.backend_pedidos.service;

public interface CorreoService {

    void enviarContrasenaTemporal(String correo, String nombreCompleto, String contrasenaTemporal);
}
