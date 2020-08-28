package opsigo.com.datalayer.database

import android.arch.persistence.room.*

@Dao
interface OrderDatabaseDao {

    @Query("SELECT * from OrderDbEntity")
    fun getAll(): List<OrderDbEntity>

    @Query("SELECT * FROM OrderDbEntity WHERE typeAccomodation = :typeAccomodation")
    abstract fun findByName(typeAccomodation:String): MutableList<OrderDbEntity>

    @Query("SELECT * FROM OrderDbEntity WHERE typeAccomodation = :typeAccomodation AND statusOrder = :status")
    abstract fun findByNameAndStatus(typeAccomodation:String,status:String): MutableList<OrderDbEntity>

    @Insert
    abstract fun insertAll(order: OrderDbEntity)

}