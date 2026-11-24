package pe.idat.app_pedidos.retrofit

import pe.idat.app_pedidos.retrofit.api.AuthService
import pe.idat.app_pedidos.retrofit.api.CatalogoService
import pe.idat.app_pedidos.retrofit.api.ClienteService
import pe.idat.app_pedidos.retrofit.api.PedidoService
import pe.idat.app_pedidos.retrofit.api.SucursalService
import pe.idat.app_pedidos.util.Constantes.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // Emulador de Android Studio: 10.0.2.2 apunta al localhost de tu PC (backend en el puerto 8081).
    // Celular físico: usa la IP de tu PC en la misma red WiFi, ej. "http://192.168.1.50:8081/".


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val catalogoService: CatalogoService by lazy { retrofit.create(CatalogoService::class.java) }
    val authService: AuthService by lazy { retrofit.create(AuthService::class.java) }
    val clienteService: ClienteService by lazy { retrofit.create(ClienteService::class.java) }
    val sucursalService: SucursalService by lazy { retrofit.create(SucursalService::class.java) }
    val pedidoService: PedidoService by lazy { retrofit.create(PedidoService::class.java) }
}
