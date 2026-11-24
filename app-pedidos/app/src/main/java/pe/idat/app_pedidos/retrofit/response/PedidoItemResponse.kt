package pe.idat.app_pedidos.retrofit.response

import java.math.BigDecimal

data class PedidoItemResponse(
    var producto: String,
    var opcion: String,
    var cantidad: Int,
    var precioUnitario: BigDecimal,
    var subtotal: BigDecimal
)
