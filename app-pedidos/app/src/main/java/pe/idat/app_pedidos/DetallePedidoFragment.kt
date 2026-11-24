package pe.idat.app_pedidos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import pe.idat.app_pedidos.adapter.LineaPedidoAdapter
import pe.idat.app_pedidos.databinding.FragmentDetallePedidoBinding
import pe.idat.app_pedidos.db.BananosRoomDatabase
import pe.idat.app_pedidos.retrofit.RetrofitClient
import pe.idat.app_pedidos.retrofit.response.ErrorResponse
import pe.idat.app_pedidos.retrofit.response.PedidoDetalleResponse
import pe.idat.app_pedidos.util.FormatoPedido
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** M05 · Detalle de pedido (Figma: "order"). HU15: GET /api/clientes/{idCliente}/pedidos/{idPedido} y "Compartir pedido". */
class DetallePedidoFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentDetallePedidoBinding? = null
    private val binding get() = _binding!!

    private var idPedido = 0
    private var idCliente = 0
    private var pedido: PedidoDetalleResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetallePedidoBinding.inflate(
            inflater, container, false)
        idPedido = arguments?.getInt("idPedido") ?: 0
        binding.rvProductos.layoutManager =
            LinearLayoutManager(requireActivity())
        binding.btnVolver.setOnClickListener(this)
        binding.btnCompartir.setOnClickListener(this)
        binding.btnReintentar.setOnClickListener(this)
        obtenerClienteLogueado()
        return binding.root
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnVolver -> findNavController().navigateUp()
            R.id.btnCompartir -> compartirPedido()
            R.id.btnReintentar -> getDetalle()
        }
    }

    fun obtenerClienteLogueado() {
        val db = BananosRoomDatabase.getDatabase(
            requireContext())
        db.clienteDao().obtener().observe(
            viewLifecycleOwner, Observer {
                cliente ->
                if (cliente != null) {
                    idCliente = cliente.idCliente
                    getDetalle()
                }
            }
        )
    }

    fun getDetalle() {
        binding.progreso.isVisible = true
        binding.layoutError.isVisible = false

        val request: Call<PedidoDetalleResponse> =
            RetrofitClient.pedidoService
                .obtenerDetalle(idCliente, idPedido)
        request.enqueue(object : Callback<PedidoDetalleResponse> {
            override fun onResponse(
                call: Call<PedidoDetalleResponse>,
                response: Response<PedidoDetalleResponse>
            ) {
                if (_binding == null) return // la vista ya se destruyó
                binding.progreso.isVisible = false
                val detalle = response.body()
                if (response.isSuccessful && detalle != null) {
                    mostrarPedido(detalle)
                } else {
                    // 404: el pedido no existe o no es de este cliente
                    val error = runCatching {
                        Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    }.getOrNull()
                    mostrarError(error?.mensaje ?: getString(R.string.detalle_error))
                }
            }

            override fun onFailure(
                call: Call<PedidoDetalleResponse>,
                t: Throwable
            ) {
                if (_binding == null) return
                binding.progreso.isVisible = false
                mostrarError(getString(R.string.registro_error_conexion))
            }
        })
    }

    private fun mostrarPedido(detalle: PedidoDetalleResponse) {
        pedido = detalle
        val context = requireContext()
        binding.svContenido.isVisible = true
        binding.tvCodigo.text = detalle.codigoPedido
        // Figma: "DELIVERY" / "Bananos Callao (Chosica)" / "Efectivo"
        binding.tvModalidad.text = detalle.modalidad
        binding.tvSucursal.text = detalle.sucursal
        binding.tvMetodoPago.text = FormatoPedido.metodoPago(context, detalle.metodoPago)
        binding.rvProductos.adapter = LineaPedidoAdapter(detalle.productos)
        // Delivery: dirección exacta a la que se entregará el pedido
        binding.layoutDireccion.isVisible =
            detalle.modalidad == "DELIVERY" && !detalle.direccionEntrega.isNullOrBlank()
        binding.tvDireccion.text = detalle.direccionEntrega
    }

    private fun mostrarError(mensaje: String) {
        binding.svContenido.isVisible = false
        binding.layoutError.isVisible = true
        binding.tvError.text = mensaje
    }

    // Arma un resumen en texto y abre las opciones de compartir (WhatsApp, etc.)
    private fun compartirPedido() {
        val detalle = pedido ?: return
        val context = requireContext()
        val texto = StringBuilder()
        texto.appendLine(getString(R.string.compartir_encabezado))
        texto.appendLine(getString(R.string.compartir_codigo, detalle.codigoPedido))
        texto.appendLine(getString(R.string.compartir_fecha, FormatoPedido.fecha(detalle.fechaRegistro)))
        texto.appendLine(getString(R.string.compartir_cliente, detalle.nombreCliente, detalle.celularCliente))
        texto.appendLine(getString(R.string.compartir_modalidad, FormatoPedido.modalidad(context, detalle.modalidad)))
        texto.appendLine(getString(R.string.compartir_sucursal, detalle.sucursal))
        if (detalle.modalidad == "DELIVERY" && !detalle.direccionEntrega.isNullOrBlank()) {
            texto.appendLine(getString(R.string.compartir_direccion, detalle.direccionEntrega))
        }
        texto.appendLine(getString(R.string.compartir_pago, FormatoPedido.metodoPago(context, detalle.metodoPago)))
        texto.appendLine()
        texto.appendLine(getString(R.string.compartir_productos))
        detalle.productos.forEach {
            texto.appendLine(getString(R.string.compartir_linea,
                it.cantidad, it.producto, it.opcion, FormatoPedido.soles(it.subtotal)))
        }
        texto.appendLine()
        texto.append(getString(R.string.carrito_total, FormatoPedido.soles(detalle.montoTotal)))

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, texto.toString())
        startActivity(Intent.createChooser(intent, getString(R.string.detalle_compartir)))
    }

    override fun onDestroyView() {
        binding.rvProductos.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
