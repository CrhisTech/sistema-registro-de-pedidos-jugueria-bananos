package com.idat.backend_pedidos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "ped_producto_opcion")
@Getter
@Setter
@NoArgsConstructor
public class ProductoOpcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "I_ID_PRODUCTO_OPCION")
    private Integer idProductoOpcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "I_ID_PRODUCTO", nullable = false)
    private Producto producto;

    @Column(name = "V_NOMBRE", nullable = false, length = 60)
    private String nombre;

    @Column(name = "N_PRECIO", nullable = false, precision = 8, scale = 2)
    private BigDecimal precio;
}
