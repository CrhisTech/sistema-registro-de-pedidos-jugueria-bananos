package pe.idat.app_pedidos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import pe.idat.app_pedidos.adapter.CarritoAdapter
import pe.idat.app_pedidos.databinding.FragmentCarritoBinding
import pe.idat.app_pedidos.db.BananosRoomDatabase
import pe.idat.app_pedidos.db.entity.CarritoEntity
import java.math.BigDecimal
import java.util.Locale

/** M02 · Carrito (Figma: "cart · Tu carrito" y "cart-empty"). Los productos se guardan en SQLite (Room). */
class CarritoFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentCarritoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCarritoBinding.inflate(
            inflater, container, false)
        binding.rvCarrito.layoutManager =
            LinearLayoutManager(requireActivity())
        binding.btnVolver.setOnClickListener(this)
        binding.filaAgregarMas.setOnClickListener(this)
        binding.btnExplorarCarta.setOnClickListener(this)
        binding.btnContinuar.setOnClickListener(this)
        obtenerCarrito()
        return binding.root
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnVolver,
            R.id.filaAgregarMas,
            R.id.btnExplorarCarta -> irACarta()
            R.id.btnContinuar -> findNavController()
                .navigate(R.id.action_carritoFragment_to_registrarPedidoFragment)
        }
    }

    // Lee el carrito de SQLite; se vuelve a pintar cada vez que cambia una cantidad
    fun obtenerCarrito() {
        val db = BananosRoomDatabase.getDatabase(
            requireContext())
        db.carritoDao().obtenerTodo().observe(
            viewLifecycleOwner, Observer {
                listCarrito ->
                binding.rvCarrito.adapter = CarritoAdapter(
                    listCarrito, ::cambiarCantidad)
                binding.layoutProductos.isVisible = listCarrito.isNotEmpty()
                binding.layoutVacio.isVisible = listCarrito.isEmpty()
                binding.tvTotal.text = getString(
                    R.string.carrito_total, formatoSoles(calcularTotal(listCarrito)))
            }
        )
    }

    // Total = suma de (precio de la presentación x cantidad). BigDecimal para no perder céntimos.
    private fun calcularTotal(listCarrito: List<CarritoEntity>): BigDecimal =
        listCarrito.fold(BigDecimal.ZERO) { total, producto ->
            total.add(BigDecimal.valueOf(producto.precio)
                .multiply(BigDecimal(producto.cantidad)))
        }

    private fun cambiarCantidad(producto: CarritoEntity, cantidad: Int) {
        val context = requireContext().applicationContext
        Thread {
            val dao = BananosRoomDatabase.getDatabase(context).carritoDao()
            if (cantidad <= 0) {
                dao.eliminar(producto)
            } else {
                dao.actualizar(producto.copy(cantidad = cantidad))
            }
        }.start()
    }

    // Cambia a la pestaña Carta de la barra inferior
    private fun irACarta() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
            .selectedItemId = R.id.cartaFragment
    }

    // 7.00 -> "S/ 7,00" (formato del diseño en Figma)
    private fun formatoSoles(precio: BigDecimal): String =
        "S/ " + String.format(Locale.ROOT, "%.2f", precio).replace('.', ',')

    override fun onDestroyView() {
        binding.rvCarrito.adapter = null
        _binding = null
        super.onDestroyView()
    }
}
