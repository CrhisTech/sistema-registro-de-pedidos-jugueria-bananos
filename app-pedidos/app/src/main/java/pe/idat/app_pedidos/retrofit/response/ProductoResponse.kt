package pe.idat.app_pedidos.retrofit.response

data class ProductoResponse(
    var idProducto: Int,
    var idCategoria: Int,
    var categoria: String,
    var nombre: String,
    var descripcion: String?,
    var urlImagen: String?,
    var opciones: List<ProductoOpcionResponse>
)
