package pe.idat.app_pedidos.retrofit.api

import pe.idat.app_pedidos.retrofit.request.RegistrarPedidoRequest
import pe.idat.app_pedidos.retrofit.response.PedidoDetalleResponse
import pe.idat.app_pedidos.retrofit.response.PedidoResumenResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PedidoService {

    // POST /api/clientes/{idCliente}/pedidos  -> 201 con el pedido registrado (código BAN-XXXX)
    @POST("api/clientes/{idCliente}/pedidos")
    fun registrarPedido(
        @Path("idCliente") idCliente: Int,
        @Body request: RegistrarPedidoRequest
    ): Call<PedidoDetalleResponse>

    // GET /api/clientes/{idCliente}/pedidos  -> del más reciente al más antiguo
    @GET("api/clientes/{idCliente}/pedidos")
    fun listarPedidos(@Path("idCliente") idCliente: Int): Call<List<PedidoResumenResponse>>

    // GET /api/clientes/{idCliente}/pedidos/{idPedido}  -> 404 si el pedido no es de ese cliente
    @GET("api/clientes/{idCliente}/pedidos/{idPedido}")
    fun obtenerDetalle(
        @Path("idCliente") idCliente: Int,
        @Path("idPedido") idPedido: Int
    ): Call<PedidoDetalleResponse>
}
