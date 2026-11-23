package pe.idat.app_pedidos.retrofit.api

import pe.idat.app_pedidos.retrofit.request.LoginRequest
import pe.idat.app_pedidos.retrofit.request.RecuperarContrasenaRequest
import pe.idat.app_pedidos.retrofit.request.RegistroClienteRequest
import pe.idat.app_pedidos.retrofit.response.ClienteResponse
import pe.idat.app_pedidos.retrofit.response.MensajeResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    // POST /api/auth/registro  -> 201 Created con los datos del cliente
    @POST("api/auth/registro")
    suspend fun registrar(@Body request: RegistroClienteRequest): ClienteResponse

    // POST /api/auth/login  -> 200 con los datos del cliente | 401 si las credenciales no son válidas
    @POST("api/auth/login")
    fun iniciarSesion(@Body request: LoginRequest): Call<ClienteResponse>

    // Recuperar contraseña
    @POST("api/auth/recuperar-contrasena")
    fun recuperarContrasena(@Body request: RecuperarContrasenaRequest): Call<MensajeResponse>
}
