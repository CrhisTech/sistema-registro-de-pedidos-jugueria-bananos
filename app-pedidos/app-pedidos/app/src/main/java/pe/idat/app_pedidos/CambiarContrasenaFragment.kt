package pe.idat.app_pedidos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import pe.idat.app_pedidos.databinding.FragmentCambiarContrasenaBinding
import pe.idat.app_pedidos.retrofit.RetrofitClient
import pe.idat.app_pedidos.retrofit.request.CambiarContrasenaRequest
import pe.idat.app_pedidos.retrofit.response.ErrorResponse
import pe.idat.app_pedidos.retrofit.response.MensajeResponse
import pe.idat.app_pedidos.util.Sesion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CambiarContrasenaFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentCambiarContrasenaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCambiarContrasenaBinding.inflate(
            inflater, container, false)
        binding.btnVolver.setOnClickListener(this)
        binding.btnCambiar.setOnClickListener(this)
        return binding.root
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
        binding.tilConfirmar.error = null

        val actual = binding.etActual.text.toString()
        val nueva = binding.etNueva.text.toString()
        val confirmar = binding.etConfirmar.text.toString()

        if (actual.isBlank()) {
            binding.tilActual.error =
                getString(R.string.cambiar_error_actual)
            return false
        }

        if (nueva.isBlank() || nueva.length !in 8..72) {
            binding.tilNueva.error =
                getString(R.string.cambiar_error_nueva)
            return false
        }

        if (actual == nueva) {
            binding.tilNueva.error =
                getString(R.string.cambiar_error_repetida)
            return false
        }

        if (confirmar.isBlank()) {
            binding.tilConfirmar.error =
                getString(R.string.cambiar_error_confirmar)
            return false
        }

        if (nueva != confirmar) {
            binding.tilConfirmar.error =
                getString(R.string.cambiar_error_coincidencia)
            return false
        }

        return true
    }

    private fun cambiarContrasena() {
        if (!validar()) return

        val idCliente = Sesion.idCliente(requireContext())

        if (idCliente == Sesion.SIN_SESION) {
            Toast.makeText(
                requireContext(),
                getString(R.string.cambiar_error_sesion),
                Toast.LENGTH_LONG
            ).show()
            return
        }

        binding.btnCambiar.isEnabled = false

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
