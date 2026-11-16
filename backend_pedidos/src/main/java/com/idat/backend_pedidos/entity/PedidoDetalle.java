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
@Table(name = "ped_pedido_detalle")
@Getter
@Setter
@NoArgsConstructor
public class PedidoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "I_ID_PEDIDO_DETALLE")
    private Integer idPedidoDetalle;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "I_ID_PEDIDO", nullable = false)
    private Pedido pedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "I_ID_PRODUCTO_OPCION", nullable = false)
    private ProductoOpcion productoOpcion;

    @Column(name = "I_CANTIDAD", nullable = false)
    private Integer cantidad;

    @Column(name = "N_PRECIO_UNITARIO", nullable = false, precision = 8, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "N_SUBTOTAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal;
}
