package pe.idat.app_pedidos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import pe.idat.app_pedidos.databinding.FragmentPedidoRegistradoBinding

/** M04 · Solicitud registrada (Figma: "success"). HU13: código del pedido, total y accesos. */
class PedidoRegistradoFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPedidoRegistradoBinding? = null
    private val binding get() = _binding!!
    private var idPedido = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPedidoRegistradoBinding.inflate(
            inflater, container, false)
        arguments?.let {
            idPedido = it.getInt("idPedido")
            binding.tvCodigo.text = it.getString("codigoPedido")
            binding.tvTotal.text = getString(
                R.string.carrito_total, it.getString("montoTotal"))
        }
        binding.btnVolver.setOnClickListener(this)
        binding.btnVerPedido.setOnClickListener(this)
        binding.filaVolverCarta.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnVolver,
            R.id.filaVolverCarta -> irAPestana(R.id.cartaFragment)
            R.id.btnVerPedido -> findNavController().navigate(
                R.id.action_pedidoRegistradoFragment_to_detallePedidoFragment,
                bundleOf("idPedido" to idPedido))
        }
    }

    // Cambia de pestaña en la barra inferior
    private fun irAPestana(idPestana: Int) {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
            .selectedItemId = idPestana
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
