package pe.idat.app_pedidos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import pe.idat.app_pedidos.databinding.FragmentCambiarContrasenaBinding
import pe.idat.app_pedidos.retrofit.RetrofitClient
import pe.idat.app_pedidos.retrofit.request.CambiarContrasenaRequest
import pe.idat.app_pedidos.retrofit.response.ErrorResponse
import pe.idat.app_pedidos.retrofit.response.MensajeResponse
import pe.idat.app_pedidos.db.BananosRoomDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** M06 · Cambiar contraseña (Figma: "security · Cambiar contraseña"). PUT /api/clientes/{idCliente}/contrasena. */
class CambiarContrasenaFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentCambiarContrasenaBinding? = null
    private val binding get() = _binding!!
    private var idCliente = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCambiarContrasenaBinding.inflate(
            inflater, container, false)
        binding.btnVolver.setOnClickListener(this)
        binding.btnCambiar.setOnClickListener(this)
        obtenerClienteLogueado()
        return binding.root
    }

    // Lee de SQLite (Room) el cliente con la sesión iniciada
    fun obtenerClienteLogueado() {
        val db = BananosRoomDatabase.getDatabase(
            requireContext())
        db.clienteDao().obtener().observe(
            viewLifecycleOwner, Observer {
                cliente ->
                if (cliente != null) {
                    idCliente = cliente.idCliente
                }
            }
        )
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnVolver -> findNavController().navigateUp()
            R.id.btnCambiar -> cambiarContrasena()
        }
    }

    /** Mismas reglas que CambiarContrasenaRequest del backend. */
    private fun validar(): Boolean {
        binding.tilActual.error = null
        binding.tilNueva.error = null

        if (binding.etActual.text.toString().isEmpty()) {
            binding.tilActual.error = getString(R.string.cambiar_error_actual)
            return false
        }
        if (binding.etNueva.text.toString().length !in 8..72) {
            binding.tilNueva.error = getString(R.string.cambiar_error_nueva)
            return false
        }
        return true
    }

    private fun cambiarContrasena() {
        if (!validar()) return
        binding.btnCambiar.isEnabled = false // evita enviar dos veces

        val request: Call<MensajeResponse> =
            RetrofitClient.clienteService
                .cambiarContrasena(
                    idCliente,
                    CambiarContrasenaRequest(
                        binding.etActual.text.toString(),
                        binding.etNueva.text.toString()))
        request.enqueue(object : Callback<MensajeResponse> {
            override fun onResponse(
                call: Call<MensajeResponse>,
                response: Response<MensajeResponse>
            ) {
                if (_binding == null) return
                binding.btnCambiar.isEnabled = true
                val mensaje = response.body()
                if (response.isSuccessful && mensaje != null) {
                    // "Tu contraseña se cambió correctamente"
                    Toast.makeText(requireContext(),
                        mensaje.mensaje,
                        Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                } else {
                    // 400: "La contraseña actual no es correcta" o error de validación de un campo
                    val error = runCatching {
                        Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    }.getOrNull()
                    val campos = error?.campos.orEmpty()
                    when {
                        campos.isNotEmpty() -> {
                            binding.tilActual.error = campos["contrasenaActual"]
                            binding.tilNueva.error = campos["contrasenaNueva"]
                        }
                        response.code() == 400 && error?.mensaje != null ->
                            binding.tilActual.error = error.mensaje
                        else -> Toast.makeText(requireContext(),
                            getString(R.string.cambiar_error),
                            Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(
                call: Call<MensajeResponse>,
                t: Throwable
            ) {
                if (_binding == null) return
                binding.btnCambiar.isEnabled = true
                Toast.makeText(requireContext(),
                    getString(R.string.registro_error_conexion),
                    Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
