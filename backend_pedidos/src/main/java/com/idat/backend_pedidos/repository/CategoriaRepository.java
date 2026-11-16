package com.idat.backend_pedidos.repository;

import com.idat.backend_pedidos.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    List<Categoria> findAllByOrderByOrdenAsc();
}
