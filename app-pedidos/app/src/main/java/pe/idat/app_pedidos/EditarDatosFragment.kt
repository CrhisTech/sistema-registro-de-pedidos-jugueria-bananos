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
import pe.idat.app_pedidos.databinding.FragmentEditarDatosBinding
import pe.idat.app_pedidos.retrofit.RetrofitClient
import pe.idat.app_pedidos.retrofit.request.ActualizarClienteRequest
import pe.idat.app_pedidos.retrofit.response.ClienteResponse
import pe.idat.app_pedidos.retrofit.response.ErrorResponse
import pe.idat.app_pedidos.db.BananosRoomDatabase
import pe.idat.app_pedidos.db.entity.ClienteEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** M06 · Editar datos (Figma: "profile-edit · Editar datos"). GET y PUT /api/clientes/{idCliente}. */
class EditarDatosFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentEditarDatosBinding? = null
    private val binding get() = _binding!!
    private var idCliente = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditarDatosBinding.inflate(
            inflater, container, false)
        binding.btnVolver.setOnClickListener(this)
        binding.btnGuardar.setOnClickListener(this)
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
            R.id.btnVolver -> findNavController().navigateUp()
            R.id.btnGuardar -> guardar()
        }
    }

    // Carga los datos actuales en el formulario
    fun getPerfil() {
        val request: Call<ClienteResponse> =
            RetrofitClient.clienteService
                .obtenerPerfil(idCliente)
        request.enqueue(object : Callback<ClienteResponse> {
            override fun onResponse(
                call: Call<ClienteResponse>,
                response: Response<ClienteResponse>
            ) {
                if (_binding == null) return
                val cliente = response.body()
                if (response.isSuccessful && cliente != null) {
                    binding.etNombre.setText(cliente.nombreCompleto)
                    binding.etCelular.setText(cliente.celular)
                    binding.etDireccion.setText(cliente.direccion)
                    binding.etCorreo.setText(cliente.correo)
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

    /** Mismas reglas que ActualizarClienteRequest del backend. */
    private fun validar(): Boolean {
        binding.tilNombre.error = null
        binding.tilCelular.error = null
        binding.tilDireccion.error = null

        if (binding.etNombre.text.toString().isBlank()) {
            binding.tilNombre.error = getString(R.string.registro_error_nombre)
            return false
        }
        if (!binding.etCelular.text.toString().trim().matches(Regex("\\d{9}"))) {
            binding.tilCelular.error = getString(R.string.registro_error_celular)
            return false
        }
        if (binding.etDireccion.text.toString().isBlank()) {
            binding.tilDireccion.error = getString(R.string.registro_error_direccion)
            return false
        }
        return true
    }

    private fun guardar() {
        if (!validar()) return
        binding.btnGuardar.isEnabled = false // evita enviar dos veces

        val request: Call<ClienteResponse> =
            RetrofitClient.clienteService
                .actualizarPerfil(
                    idCliente,
                    ActualizarClienteRequest(
                        binding.etNombre.text.toString().trim(),
                        binding.etCelular.text.toString().trim(),
                        binding.etDireccion.text.toString().trim()))
        request.enqueue(object : Callback<ClienteResponse> {
            override fun onResponse(
                call: Call<ClienteResponse>,
                response: Response<ClienteResponse>
            ) {
                if (_binding == null) return
                binding.btnGuardar.isEnabled = true
                val cliente = response.body()
                if (response.isSuccessful && cliente != null) {
                    // Actualiza también el cliente guardado en SQLite
                    val clienteEntity = ClienteEntity(
                        cliente.idCliente,
                        cliente.nombreCompleto,
                        cliente.celular,
                        cliente.direccion,
                        cliente.correo)
                    val context = requireContext().applicationContext
                    Thread {
                        BananosRoomDatabase.getDatabase(context)
                            .clienteDao().actualizar(clienteEntity)
                    }.start()
                    Toast.makeText(requireContext(),
                        getString(R.string.editar_exito),
                        Toast.LENGTH_LONG).show()
                    findNavController().navigateUp() // vuelve a Mi perfil, que recarga los datos
                } else {
                    // 400: el backend indica qué campo está mal
                    val error = runCatching {
                        Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    }.getOrNull()
                    val campos = error?.campos.orEmpty()
                    if (campos.isNotEmpty()) {
                        binding.tilNombre.error = campos["nombreCompleto"]
                        binding.tilCelular.error = campos["celular"]
                        binding.tilDireccion.error = campos["direccion"]
                    } else {
                        Toast.makeText(requireContext(),
                            error?.mensaje ?: getString(R.string.editar_error),
                            Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(
                call: Call<ClienteResponse>,
                t: Throwable
            ) {
                if (_binding == null) return
                binding.btnGuardar.isEnabled = true
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
