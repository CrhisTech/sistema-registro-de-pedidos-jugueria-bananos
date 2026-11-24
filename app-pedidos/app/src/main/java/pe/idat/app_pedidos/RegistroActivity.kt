package pe.idat.app_pedidos

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import pe.idat.app_pedidos.databinding.ActivityRegistroBinding
import pe.idat.app_pedidos.retrofit.RetrofitClient
import pe.idat.app_pedidos.retrofit.request.RegistroClienteRequest
import pe.idat.app_pedidos.retrofit.response.ClienteResponse
import pe.idat.app_pedidos.retrofit.response.ErrorResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** M03 · Registro de cliente (Figma: "register · Crear cuenta"). Consume POST /api/auth/registro. */
class RegistroActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnVolver.setOnClickListener(this)
        binding.filaIrLogin.setOnClickListener(this)
        binding.btnRegistrar.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnVolver -> finish()
            R.id.filaIrLogin -> finish() // vuelve a "Iniciar sesión"
            R.id.btnRegistrar -> registrar()
        }
    }

    /** Mismas reglas que las anotaciones de RegistroClienteRequest en el backend. */
    private fun validar(): Boolean {
        val correo = binding.etCorreo.text.toString().trim()
        val contrasena = binding.etContrasena.text.toString()
        val confirmar = binding.etConfirmar.text.toString()
        limpiarErrores()

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
        if (correo.isEmpty()) {
            binding.tilCorreo.error = getString(R.string.registro_error_correo_obligatorio)
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            binding.tilCorreo.error = getString(R.string.registro_error_correo_invalido)
            return false
        }
        if (contrasena.length !in 8..72) {
            binding.tilContrasena.error = getString(R.string.registro_error_contrasena)
            return false
        }
        if (confirmar.isEmpty()) {
            binding.tilConfirmar.error = getString(R.string.registro_error_confirmar_obligatorio)
            return false
        }
        if (confirmar != contrasena) {
            binding.tilConfirmar.error = getString(R.string.registro_error_no_coinciden)
            return false
        }
        return true
    }

    private fun limpiarErrores() {
        binding.tilNombre.error = null
        binding.tilCelular.error = null
        binding.tilDireccion.error = null
        binding.tilCorreo.error = null
        binding.tilContrasena.error = null
        binding.tilConfirmar.error = null
    }

    private fun registrar() {
        if (!validar()) return
        binding.btnRegistrar.isEnabled = false // evita enviar dos veces

        val request: Call<ClienteResponse> = RetrofitClient
            .authService.registrar(
                RegistroClienteRequest(
                    binding.etNombre.text.toString().trim(),
                    binding.etCelular.text.toString().trim(),
                    binding.etDireccion.text.toString().trim(),
                    binding.etCorreo.text.toString().trim(),
                    binding.etContrasena.text.toString(),
                    binding.etConfirmar.text.toString())
            )
        request.enqueue(object : Callback<ClienteResponse> {
            override fun onResponse(
                call: Call<ClienteResponse>,
                response: Response<ClienteResponse>
            ) {
                binding.btnRegistrar.isEnabled = true
                val cliente = response.body()
                if (response.isSuccessful && cliente != null) {
                    Toast.makeText(applicationContext,
                        getString(R.string.registro_exito, cliente.nombreCompleto),
                        Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    // 400: el backend indica qué campo está mal | 409: el correo ya está registrado
                    val error = runCatching {
                        Gson().fromJson(response.errorBody()?.string(), ErrorResponse::class.java)
                    }.getOrNull()
                    val campos = error?.campos.orEmpty()
                    if (campos.isNotEmpty()) {
                        binding.tilNombre.error = campos["nombreCompleto"]
                        binding.tilCelular.error = campos["celular"]
                        binding.tilDireccion.error = campos["direccion"]
                        binding.tilCorreo.error = campos["correo"]
                        binding.tilContrasena.error = campos["contrasena"]
                        binding.tilConfirmar.error = campos["confirmarContrasena"]
                    } else if (response.code() == 409 && error?.mensaje != null) {
                        binding.tilCorreo.error = error.mensaje
                    } else {
                        Toast.makeText(applicationContext,
                            error?.mensaje ?: getString(R.string.registro_error_general),
                            Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(
                call: Call<ClienteResponse>,
                t: Throwable
            ) {
                binding.btnRegistrar.isEnabled = true
                Toast.makeText(applicationContext,
                    getString(R.string.registro_error_conexion),
                    Toast.LENGTH_LONG).show()
            }
        })
    }
}
