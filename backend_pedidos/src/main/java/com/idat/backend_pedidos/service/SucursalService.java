package com.idat.backend_pedidos.service;

import com.idat.backend_pedidos.dto.response.SucursalResponse;

import java.util.List;

public interface SucursalService {

    List<SucursalResponse> listarSucursales();
}
