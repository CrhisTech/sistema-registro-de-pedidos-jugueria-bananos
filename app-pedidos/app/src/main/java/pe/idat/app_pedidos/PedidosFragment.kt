package pe.idat.app_pedidos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import pe.idat.app_pedidos.adapter.PedidosAdapter
import pe.idat.app_pedidos.databinding.FragmentPedidosBinding
import pe.idat.app_pedidos.db.BananosRoomDatabase
import pe.idat.app_pedidos.retrofit.RetrofitClient
import pe.idat.app_pedidos.retrofit.response.PedidoResumenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** M05 · Mis pedidos (Figma: "orders"). HU14: GET /api/clientes/{idCliente}/pedidos. */
class PedidosFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPedidosBinding? = null
    private val binding get() = _binding!!

    private var idCliente = 0
    private var hayError = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPedidosBinding.inflate(
            inflater, container, false)
        binding.rvPedidos.layoutManager =
            LinearLayoutManager(requireActivity())
        binding.btnEstado.setOnClickListener(this)
        obtenerClienteLogueado()
        return binding.root
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            // Con error: reintenta. Sin pedidos: va a la carta.
            R.id.btnEstado -> if (hayError) getPedidos() else irACarta()
        }
    }

    // Lee de SQLite el cliente con la sesión iniciada y carga sus pedidos
    fun obtenerClienteLogueado() {
        val db = BananosRoomDatabase.getDatabase(
            requireContext())
        db.clienteDao().obtener().observe(
            viewLifecycleOwner, Observer {
                cliente ->
                // Se recarga cada vez que se abre la pestaña: un pedido recién registrado aparece de inmediato
                if (cliente != null) {
                    idCliente = cliente.idCliente
                    getPedidos()
                }
            }
        )
    }

    fun getPedidos() {
        binding.progreso.isVisible = true
        binding.layoutEstado.isVisible = false

        val request: Call<List<PedidoResumenResponse>> =
            RetrofitClient.pedidoService
                .listarPedidos(idCliente)
        request.enqueue(object : Callback<List<PedidoResumenResponse>> {
            override fun onResponse(
                call: Call<List<PedidoResumenResponse>>,
                response: Response<List<PedidoResumenResponse>>
            ) {
                if (_binding == null) return // la vista ya se destruyó
                binding.progreso.isVisible = false
                val listPedidos = response.body()
                if (response.isSuccessful && listPedidos != null) {
                    binding.rvPedidos.adapter = PedidosAdapter(
                        listPedidos, ::verDetalle)
                    if (listPedidos.isEmpty()) mostrarSinPedidos()
                } else {
                    mostrarError()
                }
            }

            override fun onFailure(
                call: Call<List<PedidoResumenResponse>>,
                t: Throwable
            ) {
                if (_binding == null) return
                binding.progreso.isVisible = false
                mostrarError()
            }
        })
    }

    private fun verDetalle(pedido: PedidoResumenResponse) {
        findNavController().navigate(
            R.id.action_pedidosFragment_to_detallePedidoFragment,
            bundleOf("idPedido" to pedido.idPedido))
    }

    private fun mostrarSinPedidos() {
        hayError = false
        binding.layoutEstado.isVisible = true
        binding.tvEstadoTitulo.text = getString(R.string.pedidos_vacio_titulo)
        binding.tvEstadoDescripcion.text = getString(R.string.pedidos_vacio_descripcion)
        binding.btnEstado.text = getString(R.string.carrito_explorar)
    }

    private fun mostrarError() {
        hayError = true
        binding.rvPedidos.adapter = null
        binding.layoutEstado.isVisible = true
        binding.tvEstadoTitulo.text = getString(R.string.pedidos_error_titulo)
        binding.tvEstadoDescripcion.text = getString(R.string.registro_error_conexion)
        binding.btnEstado.text = getString(R.string.pedidos_reintentar)
    }

    // Cambia a la pestaña Carta de la barra inferior
    private fun irACarta() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
            .selectedItemId = R.id.cartaFragment
    }

    override fun onDestroyView() {
        binding.rvPedidos.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
