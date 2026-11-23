package pe.idat.app_pedidos

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pe.idat.app_pedidos.databinding.ActivityLoginBinding
import pe.idat.app_pedidos.retrofit.RetrofitClient
import pe.idat.app_pedidos.retrofit.request.LoginRequest
import pe.idat.app_pedidos.retrofit.response.ClienteResponse
import pe.idat.app_pedidos.util.Sesion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnIniciarSesion.setOnClickListener(this)
        binding.filaCrearCuenta.setOnClickListener(this)
        binding.filaOlvideContrasena.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnIniciarSesion -> login()
            R.id.filaCrearCuenta -> startActivity(Intent(applicationContext, RegistroActivity::class.java))
            R.id.filaOlvideContrasena -> startActivity(Intent(applicationContext, RecuperarContrasenaActivity::class.java))
        }
    }

    private fun validar(): Boolean {
        val correo = binding.etCorreo.text.toString().trim()
        val contrasena = binding.etContrasena.text.toString()
        binding.tilCorreo.error = null
        binding.tilContrasena.error = null

        if (correo.isEmpty()) {
            binding.tilCorreo.error = getString(R.string.registro_error_correo_obligatorio)
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            binding.tilCorreo.error = getString(R.string.registro_error_correo_invalido)
            return false
        }
        if (contrasena.isEmpty()) {
            binding.tilContrasena.error = getString(R.string.login_error_contrasena)
            return false
        }
        return true
    }

    private fun login() {
        if (!validar()) return
        binding.btnIniciarSesion.isEnabled = false

        val request: Call<ClienteResponse> = RetrofitClient
            .authService.iniciarSesion(
                LoginRequest(
                    binding.etCorreo.text.toString().trim(),
                    binding.etContrasena.text.toString())
            )
        request.enqueue(object : Callback<ClienteResponse> {
            override fun onResponse(
                call: Call<ClienteResponse>,
                response: Response<ClienteResponse>
            ) {
                binding.btnIniciarSesion.isEnabled = true
                val cliente = response.body()
                if (response.isSuccessful && cliente != null) {
                    Sesion.guardar(applicationContext, cliente.idCliente)
                    startActivity(Intent(
                        applicationContext,
                        MainActivity::class.java))
                    finish()
                } else if (response.code() == 401) {
                    Toast.makeText(applicationContext,
                        getString(R.string.login_error_credenciales),
                        Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(applicationContext,
                        getString(R.string.login_error_general),
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<ClienteResponse>,
                t: Throwable
            ) {
                binding.btnIniciarSesion.isEnabled = true
                Toast.makeText(applicationContext,
                    getString(R.string.registro_error_conexion),
                    Toast.LENGTH_LONG).show()
            }
        })
    }
}
