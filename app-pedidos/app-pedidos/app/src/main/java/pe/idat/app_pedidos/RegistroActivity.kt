package pe.idat.app_pedidos

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import pe.idat.app_pedidos.databinding.ActivityRegistroBinding
import pe.idat.app_pedidos.retrofit.RetrofitClient
import pe.idat.app_pedidos.retrofit.request.RegistroClienteRequest
import pe.idat.app_pedidos.retrofit.response.ErrorResponse
import retrofit2.HttpException

/** M03 · Registro de cliente (Figma: "register · Crear cuenta"). Consume POST /api/auth/registro. */
class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private val authService = RetrofitClient.authService
    private var registrando = false

    // Nombre del campo en el backend -> campo de la pantalla (para pintar los errores 400)
    private val camposPorNombre: Map<String, TextInputLayout> by lazy {
        mapOf(
            "nombreCompleto" to binding.tilNombre,
            "celular" to binding.tilCelular,
            "direccion" to binding.tilDireccion,
            "correo" to binding.tilCorreo,
            "contrasena" to binding.tilContrasena,
            "confirmarContrasena" to binding.tilConfirmar
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            // Barras del sistema + teclado: así el botón no queda tapado al escribir
            val margen = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
            v.setPadding(margen.left, margen.top, margen.right, margen.bottom)
            insets
        }

        binding.btnVolver.setOnClickListener { finish() }
        binding.filaIrLogin.setOnClickListener { finish() } // vuelve a "Iniciar sesión"
        binding.btnRegistrar.setOnClickListener { registrar() }
        binding.etConfirmar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                registrar(); true
            } else false
        }

        // Al corregir un campo se borra su mensaje de error
        camposPorNombre.values.forEach { til -> til.editText?.doAfterTextChanged { til.error = null } }
    }

    private fun leerFormulario() = RegistroClienteRequest(
        nombreCompleto = binding.etNombre.text.toString().trim(),
        celular = binding.etCelular.text.toString().trim(),
        direccion = binding.etDireccion.text.toString().trim(),
        correo = binding.etCorreo.text.toString().trim(),
        contrasena = binding.etContrasena.text.toString(),
        confirmarContrasena = binding.etConfirmar.text.toString()
    )

    /** Mismas reglas que las anotaciones de RegistroClienteRequest en el backend. */
    private fun validar(r: RegistroClienteRequest): Boolean {
        val errores = linkedMapOf<TextInputLayout, String>()

        if (r.nombreCompleto.isBlank()) errores[binding.tilNombre] = getString(R.string.registro_error_nombre)
        if (!REGEX_CELULAR.matches(r.celular)) errores[binding.tilCelular] = getString(R.string.registro_error_celular)
        if (r.direccion.isBlank()) errores[binding.tilDireccion] = getString(R.string.registro_error_direccion)
        when {
            r.correo.isBlank() ->
                errores[binding.tilCorreo] = getString(R.string.registro_error_correo_obligatorio)
            !Patterns.EMAIL_ADDRESS.matcher(r.correo).matches() ->
                errores[binding.tilCorreo] = getString(R.string.registro_error_correo_invalido)
        }
        if (r.contrasena.length !in 8..72) errores[binding.tilContrasena] = getString(R.string.registro_error_contrasena)
        when {
            r.confirmarContrasena.isEmpty() ->
                errores[binding.tilConfirmar] = getString(R.string.registro_error_confirmar_obligatorio)
            r.confirmarContrasena != r.contrasena ->
                errores[binding.tilConfirmar] = getString(R.string.registro_error_no_coinciden)
        }

        camposPorNombre.values.forEach { it.error = errores[it] }
        errores.keys.firstOrNull()?.editText?.requestFocus()
        return errores.isEmpty()
    }

    private fun registrar() {
        if (registrando) return
        val request = leerFormulario()
        if (!validar(request)) return
        ocultarTeclado()

        lifecycleScope.launch {
            mostrarCargando(true)
            try {
                val cliente = authService.registrar(request)
                Toast.makeText(
                    this@RegistroActivity,
                    getString(R.string.registro_exito, cliente.nombreCompleto),
                    Toast.LENGTH_LONG
                ).show()
                finish()
            } catch (e: CancellationException) {
                throw e
            } catch (e: HttpException) {
                Log.w(TAG, "Registro rechazado: HTTP ${e.code()}")
                mostrarErrorApi(e)
            } catch (e: Exception) {
                Log.e(TAG, "Error al registrar cliente", e)
                mostrarMensaje(getString(R.string.registro_error_conexion))
            } finally {
                mostrarCargando(false)
            }
        }
    }

    /** 400 con "campos" -> error en cada campo; 409 (correo repetido) -> error en el correo; otro -> Snackbar. */
    private fun mostrarErrorApi(e: HttpException) {
        val error = e.response()?.errorBody()?.string()?.let {
            runCatching { Gson().fromJson(it, ErrorResponse::class.java) }.getOrNull()
        }
        val campos = error?.campos.orEmpty()
        when {
            campos.isNotEmpty() -> {
                campos.forEach { (campo, mensaje) -> camposPorNombre[campo]?.error = mensaje }
                camposPorNombre[campos.keys.first()]?.editText?.requestFocus()
            }
            e.code() == 409 && error?.mensaje != null -> {
                binding.tilCorreo.error = error.mensaje
                binding.etCorreo.requestFocus()
            }
            else -> mostrarMensaje(error?.mensaje ?: getString(R.string.registro_error_general))
        }
    }

    private fun mostrarCargando(cargando: Boolean) {
        registrando = cargando
        binding.progreso.isVisible = cargando
        binding.btnRegistrar.isEnabled = !cargando
        binding.btnRegistrar.text = if (cargando) "" else getString(R.string.registro_boton)
        camposPorNombre.values.forEach { it.isEnabled = !cargando }
    }

    private fun mostrarMensaje(mensaje: String) =
        Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_LONG).show()

    private fun ocultarTeclado() {
        currentFocus?.let {
            getSystemService(InputMethodManager::class.java).hideSoftInputFromWindow(it.windowToken, 0)
            it.clearFocus()
        }
    }

    private companion object {
        const val TAG = "RegistroActivity"
        val REGEX_CELULAR = Regex("\\d{9}")
    }
}
