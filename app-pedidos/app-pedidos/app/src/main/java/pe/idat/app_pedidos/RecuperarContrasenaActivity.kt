package pe.idat.app_pedidos

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import pe.idat.app_pedidos.retrofit.RetrofitClient
import pe.idat.app_pedidos.retrofit.request.RecuperarContrasenaRequest
import pe.idat.app_pedidos.retrofit.response.MensajeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecuperarContrasenaActivity : AppCompatActivity() {

    private lateinit var etCorreo: TextInputEditText
    private lateinit var tilCorreo: TextInputLayout
    private lateinit var btnRecuperar: MaterialButton
    private lateinit var progreso: CircularProgressIndicator
    private lateinit var btnVolver: ImageButton
    private lateinit var filaIrLogin: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperar_contrasena)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v, insets ->

            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            v.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        etCorreo = findViewById(R.id.etCorreo)
        tilCorreo = findViewById(R.id.tilCorreo)
        btnRecuperar = findViewById(R.id.btnRecuperar)
        progreso = findViewById(R.id.progreso)
        btnVolver = findViewById(R.id.btnVolver)
        filaIrLogin = findViewById(R.id.filaIrLogin)

        btnVolver.setOnClickListener {
            finish()
        }

        filaIrLogin.setOnClickListener {
            finish()
        }

        btnRecuperar.setOnClickListener {
            recuperarContrasena()
        }
    }

    private fun recuperarContrasena() {

        val correo = etCorreo.text.toString().trim()

        tilCorreo.error = null

        // Validar correo vacío
        if (correo.isEmpty()) {
            tilCorreo.error =
                getString(R.string.recuperar_correo_vacio)
            etCorreo.requestFocus()
            return
        }

        // Validar formato del correo
        if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            tilCorreo.error =
                getString(R.string.recuperar_correo_invalido)
            etCorreo.requestFocus()
            return
        }

        mostrarCarga(true)

        val request = RecuperarContrasenaRequest(correo)

        RetrofitClient.authService
            .recuperarContrasena(request)
            .enqueue(object : Callback<MensajeResponse> {

                override fun onResponse(
                    call: Call<MensajeResponse>,
                    response: Response<MensajeResponse>
                ) {
                    if (isFinishing || isDestroyed) return

                    mostrarCarga(false)

                    if (response.isSuccessful) {

                        MaterialAlertDialogBuilder(
                            this@RecuperarContrasenaActivity
                        )
                            .setTitle("Solicitud enviada")
                            .setMessage(
                                "Si el correo está registrado, " +
                                        "recibirás una contraseña temporal."
                            )
                            .setPositiveButton("Aceptar") { _, _ ->
                                finish()
                            }
                            .setCancelable(false)
                            .show()

                    } else {
                        mostrarMensaje(
                            getString(R.string.recuperar_error)
                        )
                    }
                }

                override fun onFailure(
                    call: Call<MensajeResponse>,
                    t: Throwable
                ) {
                    if (isFinishing || isDestroyed) return

                    mostrarCarga(false)

                    mostrarMensaje(
                        getString(
                            R.string.recuperar_error_conexion
                        )
                    )
                }
            })
    }

    private fun mostrarCarga(cargando: Boolean) {

        progreso.visibility =
            if (cargando) View.VISIBLE else View.GONE

        btnRecuperar.isEnabled = !cargando
        etCorreo.isEnabled = !cargando

        btnRecuperar.text =
            if (cargando) ""
            else getString(R.string.recuperar_boton)
    }

    private fun mostrarMensaje(mensaje: String) {

        MaterialAlertDialogBuilder(this)
            .setTitle("Recuperar contraseña")
            .setMessage(mensaje)
            .setPositiveButton("Aceptar", null)
            .show()
    }
}