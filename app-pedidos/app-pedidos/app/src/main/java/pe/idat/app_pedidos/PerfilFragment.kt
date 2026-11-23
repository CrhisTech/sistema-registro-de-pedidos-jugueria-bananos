package pe.idat.app_pedidos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import pe.idat.app_pedidos.databinding.FragmentPerfilBinding
import pe.idat.app_pedidos.retrofit.RetrofitClient
import pe.idat.app_pedidos.retrofit.response.ClienteResponse
import pe.idat.app_pedidos.util.Sesion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** M06 · Perfil (Figma: "profile · Mi perfil"). Consume GET /api/clientes/{idCliente}. */
class PerfilFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(
            inflater, container, false)
        binding.filaEditarDatos.setOnClickListener(this)
        binding.filaCambiarContrasena.setOnClickListener(this)
        binding.filaCerrarSesion.setOnClickListener(this)
        getPerfil()
        return binding.root
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.filaEditarDatos -> findNavController()
                .navigate(R.id.action_perfilFragment_to_editarDatosFragment)
            R.id.filaCambiarContrasena -> findNavController()
                .navigate(R.id.action_perfilFragment_to_cambiarContrasenaFragment)
            R.id.filaCerrarSesion -> cerrarSesion()
        }
    }

    fun getPerfil() {
        val request: Call<ClienteResponse> =
            RetrofitClient.clienteService
                .obtenerPerfil(Sesion.idCliente(requireContext()))
        request.enqueue(object : Callback<ClienteResponse> {
            override fun onResponse(
                call: Call<ClienteResponse>,
                response: Response<ClienteResponse>
            ) {
                if (_binding == null) return // la vista ya se destruyó
                val cliente = response.body()
                if (response.isSuccessful && cliente != null) {
                    binding.tvNombre.text = cliente.nombreCompleto
                    binding.tvCorreo.text = cliente.correo
                } else {
                    Toast.makeText(requireContext(),
                        getString(R.string.perfil_error),
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<ClienteResponse>,
                t: Throwable
            ) {
                if (_binding == null) return
                Toast.makeText(requireContext(),
                    getString(R.string.perfil_error),
                    Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun cerrarSesion() {
        Sesion.cerrar(requireContext())
        // Vuelve al Login y borra la pila: con "atrás" no se regresa a la app
        startActivity(
            Intent(requireContext(), LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
