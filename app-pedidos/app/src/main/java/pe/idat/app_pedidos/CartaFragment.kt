package pe.idat.app_pedidos

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import pe.idat.app_pedidos.adapter.CartaAdapter
import pe.idat.app_pedidos.databinding.FragmentCartaBinding
import pe.idat.app_pedidos.databinding.ItemChipCategoriaBinding
import pe.idat.app_pedidos.db.BananosRoomDatabase
import pe.idat.app_pedidos.db.entity.CarritoEntity
import pe.idat.app_pedidos.retrofit.RetrofitClient
import pe.idat.app_pedidos.retrofit.response.CategoriaResponse
import pe.idat.app_pedidos.retrofit.response.ProductoOpcionResponse
import pe.idat.app_pedidos.retrofit.response.ProductoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/** M01 · Catálogo (Carta): búsqueda, filtro por categoría y grilla de productos. */
class CartaFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentCartaBinding? = null
    private val binding get() = _binding!!

    private var idCategoriaSeleccionada: Int? = null // null = "Todos"
    private var textoBusqueda = ""

    // Llamada de productos en curso: se cancela si el usuario vuelve a filtrar antes de que responda
    private var requestProductos: Call<List<ProductoResponse>>? = null

    // Espera a que el usuario termine de escribir antes de buscar
    private val handler = Handler(Looper.getMainLooper())
    private val buscar = Runnable { getProductos() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartaBinding.inflate(
            inflater, container, false)
        binding.rvProductos.layoutManager =
            GridLayoutManager(requireActivity(), 2)

        binding.etBuscar.doAfterTextChanged { texto ->
            val limpio = texto?.toString()?.trim().orEmpty()
            if (limpio != textoBusqueda) {
                textoBusqueda = limpio
                handler.removeCallbacks(buscar)
                handler.postDelayed(buscar, 400)
            }
        }
        binding.etBuscar.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                requireActivity().getSystemService(InputMethodManager::class.java)
                    .hideSoftInputFromWindow(v.windowToken, 0)
                v.clearFocus()
                true
            } else false
        }

        binding.chipsCategorias.setOnCheckedStateChangeListener { group, ids ->
            val chip = ids.firstOrNull()?.let { group.findViewById<Chip>(it) }
                ?: return@setOnCheckedStateChangeListener
            val idCategoria = chip.tag as Int?
            if (idCategoria != idCategoriaSeleccionada) {
                idCategoriaSeleccionada = idCategoria
                getProductos()
            }
        }

        binding.btnReintentar.setOnClickListener(this)

        getCategorias()
        getProductos()
        return binding.root
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnReintentar -> {
                if (binding.chipsCategorias.childCount == 0) getCategorias()
                getProductos()
            }
        }
    }

    fun getCategorias() {
        val request: Call<List<CategoriaResponse>> =
            RetrofitClient.catalogoService
                .listarCategorias()
        request.enqueue(object : Callback<List<CategoriaResponse>> {
            override fun onResponse(
                call: Call<List<CategoriaResponse>>,
                response: Response<List<CategoriaResponse>>
            ) {
                if (_binding == null) return // la vista ya se destruyó
                val listCategorias = response.body()
                if (response.isSuccessful && listCategorias != null) {
                    pintarCategorias(listCategorias)
                }
            }

            override fun onFailure(
                call: Call<List<CategoriaResponse>>,
                t: Throwable
            ) {
                // Si falla, el mensaje de error lo muestra la carga de productos
                Log.e(TAG, "Error al listar categorías", t)
            }
        })
    }

    fun getProductos() {
        handler.removeCallbacks(buscar)
        requestProductos?.cancel()
        mostrarCargando()

        val request: Call<List<ProductoResponse>> =
            RetrofitClient.catalogoService
                .buscarProductos(
                    idCategoriaSeleccionada,
                    textoBusqueda.ifBlank { null })
        requestProductos = request
        request.enqueue(object : Callback<List<ProductoResponse>> {
            override fun onResponse(
                call: Call<List<ProductoResponse>>,
                response: Response<List<ProductoResponse>>
            ) {
                if (_binding == null) return
                val listProductos = response.body()
                if (response.isSuccessful && listProductos != null) {
                    binding.rvProductos.adapter = CartaAdapter(
                        listProductos, ::agregarAlCarrito)
                    mostrarProductos(listProductos.isEmpty())
                } else {
                    mostrarError()
                }
            }

            override fun onFailure(
                call: Call<List<ProductoResponse>>,
                t: Throwable
            ) {
                // Se canceló porque el usuario cambió el filtro: no es un error
                if (call.isCanceled || _binding == null) return
                Log.e(TAG, "Error al buscar productos", t)
                mostrarError()
            }
        })
    }

    private fun pintarCategorias(categorias: List<CategoriaResponse>) {
        val grupo = binding.chipsCategorias
        grupo.removeAllViews()
        // "Todos" lleva tag null: no filtra por categoría
        grupo.addView(crearChipCategoria(getString(R.string.catalogo_todos), null))
        categorias.forEach { grupo.addView(crearChipCategoria(it.nombre, it.idCategoria)) }
    }

    private fun crearChipCategoria(texto: String, idCategoria: Int?): Chip =
        ItemChipCategoriaBinding.inflate(layoutInflater, binding.chipsCategorias, false).root.apply {
            id = View.generateViewId()
            text = texto
            tag = idCategoria
            isChecked = idCategoria == idCategoriaSeleccionada
        }

    private fun mostrarCargando() {
        binding.progreso.isVisible = true
        binding.layoutEstado.isVisible = false
        binding.rvProductos.isVisible = true
    }

    private fun mostrarProductos(sinResultados: Boolean) {
        binding.progreso.isVisible = false
        binding.rvProductos.isVisible = true
        binding.layoutEstado.isVisible = sinResultados
        binding.tvEstado.text = getString(R.string.catalogo_sin_resultados)
        binding.btnReintentar.isVisible = false
    }

    private fun mostrarError() {
        binding.progreso.isVisible = false
        binding.rvProductos.isVisible = false
        binding.layoutEstado.isVisible = true
        binding.tvEstado.text = getString(R.string.catalogo_error)
        binding.btnReintentar.isVisible = true
    }

    // Guarda el producto en el carrito (SQLite). Si esa presentación ya estaba, suma 1 a la cantidad.
    private fun agregarAlCarrito(producto: ProductoResponse, opcion: ProductoOpcionResponse) {
        val context = requireContext().applicationContext
        val mensaje = getString(R.string.catalogo_agregado, producto.nombre, opcion.nombre)
        Thread {
            val dao = BananosRoomDatabase.getDatabase(context).carritoDao()
            val existente = dao.obtenerPorId(opcion.idProductoOpcion)
            if (existente != null) {
                dao.actualizar(existente.copy(cantidad = existente.cantidad + 1))
            } else {
                dao.insertar(CarritoEntity(
                    opcion.idProductoOpcion,
                    producto.idProducto,
                    producto.nombre,
                    opcion.nombre,
                    opcion.precio.toDouble(),
                    producto.urlImagen,
                    1))
            }
            activity?.runOnUiThread {
                val root = _binding?.root ?: return@runOnUiThread
                Snackbar.make(root, mensaje, Snackbar.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroyView() {
        handler.removeCallbacks(buscar)
        requestProductos?.cancel()
        binding.rvProductos.adapter = null
        _binding = null
        super.onDestroyView()
    }

    private companion object {
        const val TAG = "CartaFragment"
    }
}
