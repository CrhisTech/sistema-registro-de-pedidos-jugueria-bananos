package pe.idat.app_pedidos.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import pe.idat.app_pedidos.db.entity.CarritoEntity

@Dao
interface CarritoDao {
    //insert into carrito values(.....)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertar(producto: CarritoEntity)
    //update carrito set .... where idProductoOpcion = :idProductoOpcion
    @Update
    fun actualizar(producto: CarritoEntity)
    @Delete
    fun eliminar(producto: CarritoEntity)
    @Query("DELETE FROM carrito")
    fun eliminarTodo()
    @Query("SELECT * FROM carrito WHERE idProductoOpcion = :idProductoOpcion")
    fun obtenerPorId(idProductoOpcion: Int): CarritoEntity?
    @Query("SELECT * FROM carrito")
    fun obtenerTodo(): LiveData<List<CarritoEntity>>
}
