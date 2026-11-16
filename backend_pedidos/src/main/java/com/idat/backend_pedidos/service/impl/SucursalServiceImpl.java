package com.idat.backend_pedidos.service.impl;

import com.idat.backend_pedidos.dto.response.SucursalResponse;
import com.idat.backend_pedidos.mapper.CatalogoMapper;
import com.idat.backend_pedidos.repository.SucursalRepository;
import com.idat.backend_pedidos.service.SucursalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SucursalServiceImpl implements SucursalService {

    private final SucursalRepository sucursalRepository;
    private final CatalogoMapper catalogoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<SucursalResponse> listarSucursales() {
        return sucursalRepository.findAllByOrderByNombreAsc().stream()
                .map(catalogoMapper::toResponse)
                .toList();
    }
}
