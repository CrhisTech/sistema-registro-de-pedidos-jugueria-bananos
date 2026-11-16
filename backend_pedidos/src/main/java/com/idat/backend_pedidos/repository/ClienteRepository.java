package com.idat.backend_pedidos.repository;

import com.idat.backend_pedidos.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByCorreo(String correo);

    boolean existsByCorreo(String correo);
}
