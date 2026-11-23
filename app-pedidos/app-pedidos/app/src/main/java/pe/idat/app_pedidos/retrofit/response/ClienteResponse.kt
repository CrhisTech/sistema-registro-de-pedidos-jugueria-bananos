package pe.idat.app_pedidos.retrofit.response

data class ClienteResponse(
    var idCliente: Int,
    var nombreCompleto: String,
    var celular: String,
    var direccion: String,
    var correo: String
)
