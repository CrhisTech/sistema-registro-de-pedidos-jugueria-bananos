package com.idat.backend_pedidos.repository;

import com.idat.backend_pedidos.entity.Sucursal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SucursalRepository extends JpaRepository<Sucursal, Integer> {

    List<Sucursal> findAllByOrderByNombreAsc();
}
