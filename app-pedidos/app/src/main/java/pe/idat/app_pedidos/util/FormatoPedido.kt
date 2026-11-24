package pe.idat.app_pedidos.util

import android.content.Context
import pe.idat.app_pedidos.R
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Locale

// Textos que se muestran a partir de los valores que envía/recibe la API de pedidos
object FormatoPedido {

    // 7.00 -> "S/ 7,00" (formato del diseño en Figma)
    fun soles(monto: BigDecimal): String =
        "S/ " + String.format(Locale.ROOT, "%.2f", monto).replace('.', ',')

    // RECOJO | DELIVERY
    fun modalidad(context: Context, modalidad: String): String =
        if (modalidad == "DELIVERY") context.getString(R.string.modalidad_delivery)
        else context.getString(R.string.modalidad_recojo)

    // Versión corta para Mis pedidos: "Recojo" / "Delivery"
    fun modalidadCorta(context: Context, modalidad: String): String =
        if (modalidad == "DELIVERY") context.getString(R.string.modalidad_delivery)
        else context.getString(R.string.modalidad_recojo_corta)

    // EFECTIVO | TARJETA | BILLETERA_DIGITAL
    fun metodoPago(context: Context, metodoPago: String): String = when (metodoPago) {
        "TARJETA" -> context.getString(R.string.pago_tarjeta)
        "BILLETERA_DIGITAL" -> context.getString(R.string.pago_billetera)
        else -> context.getString(R.string.pago_efectivo)
    }

    // "2026-10-25T12:00:15.123" (LocalDateTime de la API) -> "25 oct. 2026 · 12:00" (formato del Figma)
    // Se usa SimpleDateFormat porque java.time necesita Android 8 y la app soporta desde Android 7 (minSdk 24)
    fun fecha(fechaRegistro: String): String = runCatching {
        val fecha = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ROOT)
            .parse(fechaRegistro.take(16))!!
        SimpleDateFormat("d MMM yyyy · HH:mm", Locale("es", "PE")).format(fecha)
    }.getOrDefault(fechaRegistro)
}
