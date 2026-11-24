package pe.idat.app_pedidos.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cliente")
data class ClienteEntity(
    @PrimaryKey
    val idCliente: Int,
    val nombreCompleto: String,
    val celular: String,
    val direccion: String,
    val correo: String
)
