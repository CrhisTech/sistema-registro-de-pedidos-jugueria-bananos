package com.idat.backend_pedidos.entity;

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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ped_producto")
@Getter
@Setter
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "I_ID_PRODUCTO")
    private Integer idProducto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "I_ID_CATEGORIA", nullable = false)
    private Categoria categoria;

    @Column(name = "V_NOMBRE", nullable = false, length = 120)
    private String nombre;

    @Column(name = "V_DESCRIPCION", length = 300)
    private String descripcion;

    @Column(name = "V_URL_IMAGEN", length = 500)
    private String urlImagen;

    @OneToMany(mappedBy = "producto")
    @OrderBy("idProductoOpcion ASC")
    private List<ProductoOpcion> opciones = new ArrayList<>();
}
