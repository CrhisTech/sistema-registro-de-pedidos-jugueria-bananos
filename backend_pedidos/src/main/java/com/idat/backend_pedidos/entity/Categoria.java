package com.idat.backend_pedidos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ped_categoria")
@Getter
@Setter
@NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "I_ID_CATEGORIA")
    private Integer idCategoria;

    @Column(name = "V_NOMBRE", nullable = false, length = 80, unique = true)
    private String nombre;

    @Column(name = "I_ORDEN", nullable = false)
    private Integer orden;
}
