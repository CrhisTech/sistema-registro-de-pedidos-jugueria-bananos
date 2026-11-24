package pe.idat.app_pedidos.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Producto agregado al carrito: una fila por presentación (idProductoOpcion)
@Entity(tableName = "carrito")
data class CarritoEntity(
    @PrimaryKey
    val idProductoOpcion: Int,
    val idProducto: Int,
    val nombreProducto: String,
    val nombreOpcion: String,
    val precio: Double,
    val urlImagen: String?,
    val cantidad: Int
)
