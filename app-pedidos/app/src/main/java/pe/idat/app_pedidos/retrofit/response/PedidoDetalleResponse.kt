package pe.idat.app_pedidos.retrofit.response

import java.math.BigDecimal

data class PedidoDetalleResponse(
    var idPedido: Int,
    var codigoPedido: String,
    var fechaRegistro: String,
    var nombreCliente: String,
    var celularCliente: String,
    var modalidad: String,
    var sucursal: String,
    var metodoPago: String,
    var direccionEntrega: String?,
    var productos: List<PedidoItemResponse>,
    var montoTotal: BigDecimal
)
