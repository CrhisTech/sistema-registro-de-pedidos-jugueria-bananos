package pe.idat.app_pedidos

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pe.idat.app_pedidos.databinding.ActivityRecuperarContrasenaBinding
import pe.idat.app_pedidos.retrofit.RetrofitClient
import pe.idat.app_pedidos.retrofit.request.RecuperarContrasenaRequest
import pe.idat.app_pedidos.retrofit.response.MensajeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecuperarContrasenaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRecuperarContrasenaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecuperarContrasenaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnVolver.setOnClickListener(this)
        binding.btnEnviar.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnVolver -> finish()
            R.id.btnEnviar -> recuperar()
        }
    }

    private fun validar(): Boolean {
        val correo = binding.etCorreo.text.toString().trim()
        binding.tilCorreo.error = null

        if (correo.isEmpty()) {
            binding.tilCorreo.error = getString(R.string.registro_error_correo_obligatorio)
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            binding.tilCorreo.error = getString(R.string.registro_error_correo_invalido)
            return false
        }
        return true
    }

    private fun recuperar() {
        if (!validar()) return
        binding.btnEnviar.isEnabled = false

        val request: Call<MensajeResponse> = RetrofitClient
            .authService.recuperarContrasena(
                RecuperarContrasenaRequest(
                    binding.etCorreo.text.toString().trim())
            )
        request.enqueue(object : Callback<MensajeResponse> {
            override fun onResponse(
                call: Call<MensajeResponse>,
                response: Response<MensajeResponse>
            ) {
                binding.btnEnviar.isEnabled = true
                if (response.isSuccessful) {
                    // Por seguridad el backend responde lo mismo exista o no el correo
                    Toast.makeText(applicationContext,
                        response.body()?.mensaje ?: getString(R.string.recuperar_exito),
                        Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext,
                        getString(R.string.recuperar_error_general),
                        Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<MensajeResponse>,
                t: Throwable
            ) {
                binding.btnEnviar.isEnabled = true
                Toast.makeText(applicationContext,
                    getString(R.string.registro_error_conexion),
                    Toast.LENGTH_LONG).show()
            }
        })
    }
}
