package pe.idat.app_pedidos

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import pe.idat.app_pedidos.databinding.FragmentPerfilBinding
import pe.idat.app_pedidos.retrofit.RetrofitClient
import pe.idat.app_pedidos.retrofit.response.ClienteResponse
import pe.idat.app_pedidos.db.BananosRoomDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** M06 · Perfil (Figma: "profile · Mi perfil"). Consume GET /api/clientes/{idCliente}. */
class PerfilFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private var idCliente = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(
            inflater, container, false)
        binding.filaEditarDatos.setOnClickListener(this)
        binding.filaCambiarContrasena.setOnClickListener(this)
        binding.filaCerrarSesion.setOnClickListener(this)
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
                    getPerfil()
                }
            }
        )
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
                .obtenerPerfil(idCliente)
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
        val context = requireContext().applicationContext
        Thread {
            val db = BananosRoomDatabase.getDatabase(context)
            db.carritoDao().eliminarTodo()
            db.clienteDao().eliminarTodo()
            activity?.runOnUiThread {
                startActivity(
                    Intent(context, LoginActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            }
        }.start()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
