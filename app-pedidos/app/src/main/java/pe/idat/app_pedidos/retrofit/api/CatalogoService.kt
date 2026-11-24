package pe.idat.app_pedidos.retrofit.api

import pe.idat.app_pedidos.retrofit.response.CategoriaResponse
import pe.idat.app_pedidos.retrofit.response.ProductoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CatalogoService {

    // GET /api/catalogo/categorias
    @GET("api/catalogo/categorias")
    fun listarCategorias(): Call<List<CategoriaResponse>>

    // GET /api/catalogo/productos?idCategoria=&buscar=  (si un filtro es null, Retrofit no lo envía)
    @GET("api/catalogo/productos")
    fun buscarProductos(
        @Query("idCategoria") idCategoria: Int?,
        @Query("buscar") buscar: String?
    ): Call<List<ProductoResponse>>
}
