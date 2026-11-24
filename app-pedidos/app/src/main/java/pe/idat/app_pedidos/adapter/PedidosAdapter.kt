package pe.idat.app_pedidos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pe.idat.app_pedidos.R
import pe.idat.app_pedidos.databinding.ItemPedidosBinding
import pe.idat.app_pedidos.retrofit.response.PedidoResumenResponse
import pe.idat.app_pedidos.util.FormatoPedido

class PedidosAdapter(
    private var pedidoList: List<PedidoResumenResponse>,
    private val onSeleccionar: (PedidoResumenResponse) -> Unit
) : RecyclerView.Adapter<PedidosAdapter.ViewHolder>() {

    inner class ViewHolder(
        var binding: ItemPedidosBinding
    )
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PedidosAdapter.ViewHolder {
        val binding = ItemPedidosBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PedidosAdapter.ViewHolder, position: Int) {
        with(holder) {
            with(pedidoList[position]) {
                val context = itemView.context
                // Figma: "BAN-DEMO" / "25 oct. 2026 · 12:00 · Recojo" / sucursal / "1 producto · S/ 7,00"
                binding.tvCodigo.text = codigoPedido
                binding.tvFechaModalidad.text = context.getString(
                    R.string.pedidos_fecha_modalidad,
                    FormatoPedido.fecha(fechaRegistro),
                    FormatoPedido.modalidadCorta(context, modalidad))
                binding.tvSucursal.text = sucursal
                binding.tvCantidadTotal.text = context.getString(
                    R.string.pedidos_cantidad_total,
                    context.resources.getQuantityString(
                        R.plurals.pedidos_cantidad, cantidadProductos, cantidadProductos),
                    FormatoPedido.soles(montoTotal))

                // Al tocar el pedido se abre su detalle
                val pedido = this
                itemView.setOnClickListener { onSeleccionar(pedido) }
            }
        }
    }

    override fun getItemCount(): Int = pedidoList.size
}
