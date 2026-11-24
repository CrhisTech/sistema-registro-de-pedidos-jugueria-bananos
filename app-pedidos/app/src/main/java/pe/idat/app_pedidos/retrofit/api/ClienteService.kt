package pe.idat.app_pedidos.retrofit.api

import pe.idat.app_pedidos.retrofit.request.ActualizarClienteRequest
import pe.idat.app_pedidos.retrofit.request.CambiarContrasenaRequest
import pe.idat.app_pedidos.retrofit.response.ClienteResponse
import pe.idat.app_pedidos.retrofit.response.MensajeResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ClienteService {

    // GET /api/clientes/{idCliente}
    @GET("api/clientes/{idCliente}")
    fun obtenerPerfil(@Path("idCliente") idCliente: Int): Call<ClienteResponse>

    // PUT /api/clientes/{idCliente}
    @PUT("api/clientes/{idCliente}")
    fun actualizarPerfil(
        @Path("idCliente") idCliente: Int,
        @Body request: ActualizarClienteRequest
    ): Call<ClienteResponse>

    // PUT /api/clientes/{idCliente}/contrasena  -> 400 si la contraseña actual no es correcta
    @PUT("api/clientes/{idCliente}/contrasena")
    fun cambiarContrasena(
        @Path("idCliente") idCliente: Int,
        @Body request: CambiarContrasenaRequest
    ): Call<MensajeResponse>
}
