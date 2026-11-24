package pe.idat.app_pedidos.retrofit.response

import java.math.BigDecimal

data class ProductoOpcionResponse(
    var idProductoOpcion: Int,
    var nombre: String,
    var precio: BigDecimal
)
