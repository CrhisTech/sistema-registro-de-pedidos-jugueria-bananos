package pe.idat.app_pedidos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import pe.idat.app_pedidos.adapter.LineaPedidoAdapter
import pe.idat.app_pedidos.databinding.FragmentRevisarPedidoBinding
import pe.idat.app_pedidos.db.BananosRoomDatabase
import pe.idat.app_pedidos.db.entity.CarritoEntity
import pe.idat.app_pedidos.retrofit.RetrofitClient
import pe.idat.app_pedidos.retrofit.request.DetallePedidoRequest
import pe.idat.app_pedidos.retrofit.request.RegistrarPedidoRequest
import pe.idat.app_pedidos.retrofit.response.ErrorResponse
import pe.idat.app_pedidos.retrofit.response.PedidoDetalleResponse
import pe.idat.app_pedidos.retrofit.response.PedidoItemResponse
import pe.idat.app_pedidos.util.FormatoPedido
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigDecimal

/** M04 · Revisa tu solicitud (Figma: "checkout-review"). HU13: POST /api/clientes/{idCliente}/pedidos. */
class RevisarPedidoFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentRevisarPedidoBinding? = null
    private val binding get() = _binding!!

    // Datos elegidos en Registrar pedido (llegan en el Bundle)
    private var idSucursal = 0
    private var sucursal = ""
    private var modalidad = ""
    private var metodoPago = ""

    private var idCliente = 0
    private var listCarrito: List<CarritoEntity> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRevisarPedidoBinding.inflate(
            inflater, container, false)
        arguments?.let {
            idSucursal = it.getInt("idSucursal")
            sucursal = it.getString("sucursal").orEmpty()
            modalidad = it.getString("modalidad").orEmpty()
            metodoPago = it.getString("metodoPago").orEmpty()
        }
        binding.rvProductos.layoutManager =
            LinearLayoutManager(requireActivity())
        binding.btnVolver.setOnClickListener(this)
        binding.btnRegistrar.setOnClickListener(this)

        // Figma: "RECOJO" / "Bananos Callao (Chosica)" / "Efectivo"
        binding.tvModalidad.text = modalidad
        binding.tvSucursal.text = sucursal
        binding.tvMetodoPago.text = FormatoPedido.metodoPago(requireContext(), metodoPago)

        obtenerClienteLogueado()
        obtenerCarrito()
        return binding.root
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnVolver -> findNavController().navigateUp()
            R.id.btnRegistrar -> registrarPedido()
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
                    // Delivery: se entrega en la dirección registrada del cliente
                    binding.layoutDireccion.isVisible = modalidad == "DELIVERY"
                    binding.tvDireccion.text = cliente.direccion
                }
            }
        )
    }

    fun obtenerCarrito() {
        val db = BananosRoomDatabase.getDatabase(
            requireContext())
        db.carritoDao().obtenerTodo().observe(
            viewLifecycleOwner, Observer {
                carrito ->
                listCarrito = carrito
                val lineas = carrito.map {
                    val precio = BigDecimal.valueOf(it.precio)
                    PedidoItemResponse(
                        it.nombreProducto,
                        it.nombreOpcion,
                        it.cantidad,
                        precio,
                        precio.multiply(BigDecimal(it.cantidad)))
                }
                binding.rvProductos.adapter = LineaPedidoAdapter(lineas)
                val total = lineas.fold(BigDecimal.ZERO) { suma, linea -> suma.add(linea.subtotal) }
                binding.tvTotal.text = getString(
                    R.string.revisar_total, FormatoPedido.soles(total))
                // No se puede registrar un pedido sin productos
                binding.btnRegistrar.isEnabled = carrito.isNotEmpty()
            }
        )
    }

    private fun registrarPedido() {
        if (listCarrito.isEmpty() || idCliente == 0) return
        binding.btnRegistrar.isEnabled = false // evita registrar dos veces

        val request: Call<PedidoDetalleResponse> =
            RetrofitClient.pedidoService
                .registrarPedido(
                    idCliente,
                    RegistrarPedidoRequest(
                        idSucursal,
                        modalidad,
                        metodoPago,
                        listCarrito.map {
                            DetallePedidoRequest(it.idProductoOpcion, it.cantidad)
                        }))
        request.enqueue(object : Callback<PedidoDetalleResponse> {
            override fun onResponse(
                call: Call<PedidoDetalleResponse>,
                response: Response<PedidoDetalleResponse>
            ) {
                if (_binding == null) return
                val pedido = response.body()
                if (response.isSuccessful && pedido != null) {
                    vaciarCarrito()
                    findNavController().navigate(
                        R.id.action_revisarPedidoFragment_to_pedidoRegistradoFragment,
                        bundleOf(
                            "idPedido" to pedido.idPedido,
                            "codigoPedido" to pedido.codigoPedido,
                            "montoTotal" to FormatoPedido.soles(pedido.montoTotal)))
                } else {
                    binding.btnRegistrar.isEnabled = true
                    // 400/404: el backend explica el problema (producto o sucursal inexistente, etc.)
                    val error = runCatching {
                        Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    }.getOrNull()
                    Toast.makeText(requireContext(),
                        error?.mensaje ?: getString(R.string.revisar_error),
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<PedidoDetalleResponse>,
                t: Throwable
            ) {
                if (_binding == null) return
                binding.btnRegistrar.isEnabled = true
                Toast.makeText(requireContext(),
                    getString(R.string.registro_error_conexion),
                    Toast.LENGTH_LONG).show()
            }
        })
    }

    // El pedido ya quedó registrado: el carrito se vacía
    private fun vaciarCarrito() {
        val context = requireContext().applicationContext
        Thread {
            BananosRoomDatabase.getDatabase(context)
                .carritoDao().eliminarTodo()
        }.start()
    }

    override fun onDestroyView() {
        binding.rvProductos.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
