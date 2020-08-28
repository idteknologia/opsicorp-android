package opsigo.com.datalayer.database

import android.arch.persistence.room.Room
import android.content.Context

class AccessDb {

    fun getAll(applicationContext:Context):List<OrderDbEntity>{
        return initDb(applicationContext).userDao().getAll()
    }

    fun getByName(applicationContext: Context,typeAccomodation:String):MutableList<OrderDbEntity>{
        return initDb(applicationContext).userDao().findByName(typeAccomodation)
    }

    fun getByNameAndStatus(applicationContext: Context,typeAccomodation:String,status:String):MutableList<OrderDbEntity>{
        return initDb(applicationContext).userDao().findByNameAndStatus(typeAccomodation,status)
    }

    fun insertOrder(applicationContext: Context,orderDbEntity:OrderDbEntity){
        return initDb(applicationContext).userDao().insertAll(orderDbEntity)
    }

    fun initDb(applicationContext:Context): AppDatabase {
        return Room.databaseBuilder(applicationContext,
                AppDatabase::class.java, "order").allowMainThreadQueries().build()
    }
}

