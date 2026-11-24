package pe.idat.app_pedidos.retrofit.request

// Body de PUT /api/clientes/{idCliente}/contrasena
data class CambiarContrasenaRequest(
    var contrasenaActual: String,
    var contrasenaNueva: String
)
