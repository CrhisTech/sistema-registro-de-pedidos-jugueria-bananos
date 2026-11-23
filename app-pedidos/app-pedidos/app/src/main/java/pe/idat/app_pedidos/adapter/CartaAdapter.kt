package pe.idat.app_pedidos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import pe.idat.app_pedidos.R
import pe.idat.app_pedidos.databinding.ItemCartaBinding
import pe.idat.app_pedidos.databinding.ItemChipOpcionBinding
import pe.idat.app_pedidos.databinding.ItemPrecioOpcionBinding
import pe.idat.app_pedidos.retrofit.response.ProductoOpcionResponse
import pe.idat.app_pedidos.retrofit.response.ProductoResponse
import pe.idat.app_pedidos.util.Constantes
import java.math.BigDecimal
import java.util.Locale

class CartaAdapter(
    private var productoList: List<ProductoResponse>,
    private val onAgregar: (ProductoResponse, ProductoOpcionResponse) -> Unit
) : RecyclerView.Adapter<CartaAdapter.ViewHolder>() {

    // idProducto -> idProductoOpcion elegida (se conserva al hacer scroll)
    private val opcionSeleccionada = mutableMapOf<Int, Int>()

    inner class ViewHolder(
        var binding: ItemCartaBinding
    )
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartaAdapter.ViewHolder {
        val binding = ItemCartaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartaAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(productoList[position]) {
                val inflater = LayoutInflater.from(itemView.context)
                val unicaOpcion = opciones.size == 1

                // Nombre y descripción
                binding.tvNombre.text = nombre
                binding.tvDescripcion.text = descripcion
                binding.tvDescripcion.isVisible = !descripcion.isNullOrBlank()

                // Imagen
                val url = armarUrlImagen(urlImagen)
                binding.ivProducto.contentDescription = nombre
                binding.tvImagenIlustrativa.isVisible = url != null
                Glide.with(itemView.context)
                    .load(url)
                    .placeholder(R.drawable.bg_imagen_placeholder)
                    .error(R.drawable.bg_imagen_placeholder)
                    .fallback(R.drawable.bg_imagen_placeholder)
                    .centerCrop()
                    .into(binding.ivProducto)

                binding.contenedorPrecios.removeAllViews()
                opciones.forEach { opcion ->
                    val fila = ItemPrecioOpcionBinding.inflate(inflater, binding.contenedorPrecios, true)
                    fila.tvOpcion.text = opcion.nombre
                    fila.tvOpcion.visibility = if (unicaOpcion) View.INVISIBLE else View.VISIBLE
                    fila.tvPrecio.text = formatoSoles(opcion.precio)
                }

                // Chips para elegir la opción (no se muestran si solo hay una)
                val idElegida = opcionSeleccionada[idProducto] ?: opciones.firstOrNull()?.idProductoOpcion
                binding.chipsOpciones.setOnCheckedStateChangeListener(null)
                binding.chipsOpciones.removeAllViews()
                binding.chipsOpciones.isVisible = !unicaOpcion
                opciones.forEach { opcion ->
                    val chip = ItemChipOpcionBinding.inflate(inflater, binding.chipsOpciones, false).root
                    chip.id = View.generateViewId()
                    chip.text = opcion.nombre
                    chip.tag = opcion.idProductoOpcion
                    chip.isChecked = opcion.idProductoOpcion == idElegida
                    binding.chipsOpciones.addView(chip)
                }
                binding.chipsOpciones.setOnCheckedStateChangeListener { grupo, ids ->
                    val chip = ids.firstOrNull()?.let { grupo.findViewById<Chip>(it) }
                    if (chip != null) opcionSeleccionada[idProducto] = chip.tag as Int
                }

                // Agregar al carrito con la opción elegida
                val producto = this
                binding.btnAgregar.isEnabled = opciones.isNotEmpty()
                binding.btnAgregar.setOnClickListener {
                    val id = opcionSeleccionada[idProducto] ?: opciones.first().idProductoOpcion
                    onAgregar(producto, opciones.first { it.idProductoOpcion == id })
                }
            }
        }
    }

    override fun getItemCount(): Int = productoList.size

    // El backend guarda rutas relativas (/img/productos/papaya.jpg); Glide necesita la URL completa
    private fun armarUrlImagen(ruta: String?): String? = when {
        ruta.isNullOrBlank() -> null
        ruta.startsWith("http") -> ruta
        else -> Constantes.BASE_URL.trimEnd('/') + "/" + ruta.trimStart('/')
    }

    // 7.00 -> "S/ 7,00" (formato del diseño en Figma)
    private fun formatoSoles(precio: BigDecimal): String =
        "S/ " + String.format(Locale.ROOT, "%.2f", precio).replace('.', ',')
}
