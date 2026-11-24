package pe.idat.app_pedidos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.idat.app_pedidos.R
import pe.idat.app_pedidos.databinding.ItemLineaPedidoBinding
import pe.idat.app_pedidos.retrofit.response.PedidoItemResponse
import pe.idat.app_pedidos.util.FormatoPedido

// Productos de un pedido: se usa en "Revisa tu solicitud" (HU13) y en el Detalle de pedido (HU15)
class LineaPedidoAdapter(
    private var lineaList: List<PedidoItemResponse>
) : RecyclerView.Adapter<LineaPedidoAdapter.ViewHolder>() {

    inner class ViewHolder(
        var binding: ItemLineaPedidoBinding
    )
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LineaPedidoAdapter.ViewHolder {
        val binding = ItemLineaPedidoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LineaPedidoAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(lineaList[position]) {
                // "1 × Papaya" / "Personal" / importe (precio x cantidad)
                binding.tvProducto.text = itemView.context.getString(
                    R.string.linea_producto, cantidad, producto)
                binding.tvOpcion.text = opcion
                binding.tvPrecio.text = FormatoPedido.soles(subtotal)
            }
        }
    }

    override fun getItemCount(): Int = lineaList.size
}
