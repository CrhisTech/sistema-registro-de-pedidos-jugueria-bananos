package com.idat.backend_pedidos.entity;

import com.idat.backend_pedidos.entity.enums.MetodoPago;
import com.idat.backend_pedidos.entity.enums.Modalidad;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ped_pedido")
@Getter
@Setter
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "I_ID_PEDIDO")
    private Integer idPedido;

    @Column(name = "V_CODIGO_PEDIDO", length = 20, unique = true)
    private String codigoPedido;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "I_ID_CLIENTE", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "I_ID_SUCURSAL", nullable = false)
    private Sucursal sucursal;

    @Column(name = "C_MODALIDAD", nullable = false, length = 1)
    private Modalidad modalidad;

    @Column(name = "C_METODO_PAGO", nullable = false, length = 1)
    private MetodoPago metodoPago;

    @Column(name = "V_DIRECCION_ENTREGA", length = 250)
    private String direccionEntrega;

    @Column(name = "N_MONTO_TOTAL", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;

    @Column(name = "D_FECHA_REGISTRO", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("idPedidoDetalle ASC")
    private List<PedidoDetalle> detalles = new ArrayList<>();

    public void agregarDetalle(PedidoDetalle detalle) {
        detalle.setPedido(this);
        detalles.add(detalle);
    }

    @PrePersist
    void alRegistrar() {
        fechaRegistro = LocalDateTime.now();
    }
}
