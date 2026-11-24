package pe.idat.app_pedidos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.idat.app_pedidos.R
import pe.idat.app_pedidos.databinding.ItemCarritoBinding
import pe.idat.app_pedidos.db.entity.CarritoEntity
import pe.idat.app_pedidos.util.Constantes
import java.math.BigDecimal
import java.util.Locale

class CarritoAdapter(
    private var carritoList: List<CarritoEntity>,
    private val onCambiarCantidad: (CarritoEntity, Int) -> Unit
) : RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {

    inner class ViewHolder(
        var binding: ItemCarritoBinding
    )
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarritoAdapter.ViewHolder {
        val binding = ItemCarritoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarritoAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(carritoList[position]) {
                val precioUnitario = BigDecimal.valueOf(precio)

                binding.tvNombre.text = nombreProducto
                binding.tvDetalle.text = itemView.context.getString(
                    R.string.carrito_detalle, nombreOpcion, formatoSoles(precioUnitario))
                binding.tvCantidad.text = cantidad.toString()
                // Importe = precio de la presentación x cantidad
                binding.tvImporte.text = formatoSoles(
                    precioUnitario.multiply(BigDecimal(cantidad)))

                // Imagen
                val url = armarUrlImagen(urlImagen)
                binding.ivProducto.contentDescription = nombreProducto
                binding.tvImagenIlustrativa.isVisible = url != null
                Glide.with(itemView.context)
                    .load(url)
                    .placeholder(R.drawable.bg_imagen_placeholder)
                    .error(R.drawable.bg_imagen_placeholder)
                    .fallback(R.drawable.bg_imagen_placeholder)
                    .centerCrop()
                    .into(binding.ivProducto)

                // + / −  (con 0 el producto sale del carrito)
                val producto = this
                binding.btnMas.setOnClickListener {
                    onCambiarCantidad(producto, cantidad + 1)
                }
                binding.btnMenos.setOnClickListener {
                    onCambiarCantidad(producto, cantidad - 1)
                }
            }
        }
    }

    override fun getItemCount(): Int = carritoList.size

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
