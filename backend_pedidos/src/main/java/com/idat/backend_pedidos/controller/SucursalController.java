package com.idat.backend_pedidos.controller;

import com.idat.backend_pedidos.dto.response.SucursalResponse;
import com.idat.backend_pedidos.service.SucursalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor
public class SucursalController {

    private final SucursalService sucursalService;

    @GetMapping
    public List<SucursalResponse> listarSucursales() {
        return sucursalService.listarSucursales();
    }
}
