package pe.idat.app_pedidos.retrofit.api

import pe.idat.app_pedidos.retrofit.response.SucursalResponse
import retrofit2.Call
import retrofit2.http.GET

interface SucursalService {

    // GET /api/sucursales
    @GET("api/sucursales")
    fun listarSucursales(): Call<List<SucursalResponse>>
}
