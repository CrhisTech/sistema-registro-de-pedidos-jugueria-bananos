package pe.idat.app_pedidos.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import pe.idat.app_pedidos.db.entity.ClienteEntity

@Dao
interface ClienteDao {
    //insert into cliente values(.....)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertar(cliente: ClienteEntity)
    //update cliente set .... where idCliente = :idCliente
    @Update
    fun actualizar(cliente: ClienteEntity)
    @Query("DELETE FROM cliente")
    fun eliminarTodo()
    @Query("SELECT * FROM cliente LIMIT 1")
    fun obtener(): LiveData<ClienteEntity>
}
