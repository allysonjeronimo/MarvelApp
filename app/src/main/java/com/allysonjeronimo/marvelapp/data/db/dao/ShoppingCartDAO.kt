package com.allysonjeronimo.marvelapp.data.db.dao

import androidx.room.*
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCart

@Dao
interface ShoppingCartDAO {
    @Insert
    suspend fun insert(shoppingCart:ShoppingCart) : Long

    @Update
    suspend fun update(shoppingCart: ShoppingCart)

    @Query("delete from ShoppingCart where id = :id")
    suspend fun delete(id:Long)

    @Query("select * from ShoppingCart where id = :id")
    suspend fun find(id:Long)
}