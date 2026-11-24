package pe.idat.app_pedidos.retrofit.request

// Body de PUT /api/clientes/{idCliente} (el correo no se puede cambiar)
data class ActualizarClienteRequest(
    var nombreCompleto: String,
    var celular: String,
    var direccion: String
)
