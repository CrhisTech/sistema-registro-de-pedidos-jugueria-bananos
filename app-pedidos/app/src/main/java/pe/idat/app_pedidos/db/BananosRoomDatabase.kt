package pe.idat.app_pedidos.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.idat.app_pedidos.db.dao.CarritoDao
import pe.idat.app_pedidos.db.dao.ClienteDao
import pe.idat.app_pedidos.db.entity.CarritoEntity
import pe.idat.app_pedidos.db.entity.ClienteEntity

//create database ....
// version 2: se agregó la tabla carrito
@Database(entities = [ClienteEntity::class, CarritoEntity::class], version = 2)
abstract class BananosRoomDatabase :
    RoomDatabase() {

    abstract fun clienteDao(): ClienteDao
    abstract fun carritoDao(): CarritoDao

    companion object {
        @Volatile
        private var INSTANCE: BananosRoomDatabase? = null
        fun getDatabase(context: Context):
                BananosRoomDatabase {
            //PATRÓN SINGLETON
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BananosRoomDatabase::class.java,
                    "bananosbd")
                    // Si cambia la versión, borra y vuelve a crear las tablas (solo guarda sesión y carrito)
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
