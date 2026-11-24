package pe.idat.app_pedidos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pe.idat.app_pedidos.databinding.FragmentRegistrarPedidoBinding
import pe.idat.app_pedidos.retrofit.RetrofitClient
import pe.idat.app_pedidos.retrofit.response.SucursalResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** M04 · Registrar pedido (Figma: "checkout"). HU12: modalidad, sucursal y método de pago. GET /api/sucursales. */
class RegistrarPedidoFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentRegistrarPedidoBinding? = null
    private val binding get() = _binding!!

    private var listSucursales: List<SucursalResponse> = emptyList()
    private var sucursalSeleccionada: SucursalResponse? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrarPedidoBinding.inflate(
            inflater, container, false)
        binding.btnVolver.setOnClickListener(this)
        binding.btnContinuar.setOnClickListener(this)
        binding.rgModalidad.setOnCheckedChangeListener { _, _ ->
            binding.tvErrorModalidad.isVisible = false
        }
        binding.rgMetodoPago.setOnCheckedChangeListener { _, _ ->
            binding.tvErrorPago.isVisible = false
        }
        binding.actvSucursal.setOnItemClickListener { _, _, position, _ ->
            sucursalSeleccionada = listSucursales[position]
            binding.tilSucursal.error = null
        }
        getSucursales()
        return binding.root
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnVolver -> findNavController().navigateUp()
            R.id.btnContinuar -> continuar()
        }
    }

    fun getSucursales() {
        val request: Call<List<SucursalResponse>> =
            RetrofitClient.sucursalService
                .listarSucursales()
        request.enqueue(object : Callback<List<SucursalResponse>> {
            override fun onResponse(
                call: Call<List<SucursalResponse>>,
                response: Response<List<SucursalResponse>>
            ) {
                if (_binding == null) return // la vista ya se destruyó
                val sucursales = response.body()
                if (response.isSuccessful && sucursales != null) {
                    listSucursales = sucursales
                    // setSimpleItems no filtra la lista por el texto ya elegido
                    binding.actvSucursal.setSimpleItems(
                        sucursales.map { it.nombre }.toTypedArray())
                } else {
                    Toast.makeText(requireContext(),
                        getString(R.string.pedido_error_sucursales),
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<List<SucursalResponse>>,
                t: Throwable
            ) {
                if (_binding == null) return
                Toast.makeText(requireContext(),
                    getString(R.string.registro_error_conexion),
                    Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun validar(): Boolean {
        var valido = true
        if (binding.rgModalidad.checkedRadioButtonId == View.NO_ID) {
            binding.tvErrorModalidad.isVisible = true
            valido = false
        }
        if (sucursalSeleccionada == null) {
            binding.tilSucursal.error = getString(R.string.pedido_error_sucursal)
            valido = false
        }
        if (binding.rgMetodoPago.checkedRadioButtonId == View.NO_ID) {
            binding.tvErrorPago.isVisible = true
            valido = false
        }
        return valido
    }

    private fun continuar() {
        if (!validar()) return
        val sucursal = sucursalSeleccionada!!
        val modalidad =
            if (binding.rbDelivery.isChecked) "DELIVERY" else "RECOJO"
        val metodoPago = when (binding.rgMetodoPago.checkedRadioButtonId) {
            R.id.rbTarjeta -> "TARJETA"
            R.id.rbBilletera -> "BILLETERA_DIGITAL"
            else -> "EFECTIVO"
        }
        findNavController().navigate(
            R.id.action_registrarPedidoFragment_to_revisarPedidoFragment,
            bundleOf(
                "idSucursal" to sucursal.idSucursal,
                "sucursal" to sucursal.nombre,
                "modalidad" to modalidad,
                "metodoPago" to metodoPago))
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
