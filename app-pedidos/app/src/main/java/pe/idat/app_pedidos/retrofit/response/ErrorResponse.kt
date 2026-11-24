package pe.idat.app_pedidos.retrofit.response

// Cuerpo de error que devuelve el GlobalExceptionHandler del backend.
// "campos" solo viene en errores de validación (400): nombreCampo -> mensaje.
data class ErrorResponse(
    var fecha: String?,
    var estado: Int?,
    var error: String?,
    var mensaje: String?,
    var campos: Map<String, String>?
)
