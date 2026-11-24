package pe.idat.app_pedidos.retrofit.request

// Body de POST /api/clientes/{idCliente}/pedidos
// modalidad: RECOJO | DELIVERY   metodoPago: EFECTIVO | TARJETA | BILLETERA_DIGITAL
data class RegistrarPedidoRequest(
    var idSucursal: Int,
    var modalidad: String,
    var metodoPago: String,
    var detalles: List<DetallePedidoRequest>
)
