package com.idat.backend_pedidos.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
@Table(name = "ped_cliente")
@Getter
@Setter
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "I_ID_CLIENTE")
    private Integer idCliente;

    @Column(name = "V_NOMBRE_COMPLETO", nullable = false, length = 150)
    private String nombreCompleto;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(name = "C_CELULAR", nullable = false, length = 9)
    private String celular;

    @Column(name = "V_DIRECCION", nullable = false, length = 250)
    private String direccion;

    @Column(name = "V_CORREO", nullable = false, length = 150, unique = true)
    private String correo;

    @Column(name = "V_CONTRASENA", nullable = false, length = 100)
    private String contrasena;

    @Column(name = "D_FECHA_REGISTRO", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    void alRegistrar() {
        fechaRegistro = LocalDateTime.now();
    }
}
