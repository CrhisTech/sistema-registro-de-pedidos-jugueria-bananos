package pe.idat.app_pedidos.retrofit.response

import java.math.BigDecimal

data class PedidoResumenResponse(
    var idPedido: Int,
    var codigoPedido: String,
    var fechaRegistro: String,
    var modalidad: String,
    var sucursal: String,
    var cantidadProductos: Int,
    var montoTotal: BigDecimal
)
