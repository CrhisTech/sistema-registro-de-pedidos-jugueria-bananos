package com.idat.backend_pedidos.mapper;

import com.idat.backend_pedidos.dto.request.RegistroClienteRequest;
import com.idat.backend_pedidos.dto.response.ClienteResponse;
import com.idat.backend_pedidos.entity.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(RegistroClienteRequest request) {
        Cliente cliente = new Cliente();
        cliente.setNombreCompleto(request.nombreCompleto().trim());
        cliente.setCelular(request.celular());
        cliente.setDireccion(request.direccion().trim());
        cliente.setCorreo(request.correo().trim().toLowerCase());
        return cliente;
    }

    public ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getIdCliente(),
                cliente.getNombreCompleto(),
                cliente.getCelular(),
                cliente.getDireccion(),
                cliente.getCorreo()
        );
    }
}
