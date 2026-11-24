package pe.idat.app_pedidos.retrofit.request

// Body de POST /api/auth/registro (mismos nombres que el record RegistroClienteRequest del backend)
data class RegistroClienteRequest(
    var nombreCompleto: String,
    var celular: String,
    var direccion: String,
    var correo: String,
    var contrasena: String,
    var confirmarContrasena: String
)
