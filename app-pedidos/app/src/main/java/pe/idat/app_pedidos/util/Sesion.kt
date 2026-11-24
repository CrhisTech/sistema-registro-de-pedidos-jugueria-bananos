package pe.idat.app_pedidos.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

/** Guarda el id del cliente que inició sesión (lo necesitan Perfil, Pedidos y Carrito). */
object Sesion {

    private const val ARCHIVO = "sesion_bananos"
    private const val ID_CLIENTE = "idCliente"
    const val SIN_SESION = -1

    fun guardar(context: Context, idCliente: Int) =
        preferencias(context).edit { putInt(ID_CLIENTE, idCliente) }

    fun idCliente(context: Context): Int =
        preferencias(context).getInt(ID_CLIENTE, SIN_SESION)

    fun cerrar(context: Context) =
        preferencias(context).edit { clear() }

    private fun preferencias(context: Context): SharedPreferences =
        context.getSharedPreferences(ARCHIVO, Context.MODE_PRIVATE)
}
