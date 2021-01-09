package com.allysonjeronimo.marvelapp.data.db.dao

import androidx.room.*
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCart
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartWithItems

@Dao
interface ShoppingCartDAO {
    @Insert
    fun insert(shoppingCart:ShoppingCart) : Long

    @Update
    fun update(shoppingCart: ShoppingCart)

    @Query("delete from ShoppingCart where id = :id")
    fun delete(id:Long)

    @Query("select * from ShoppingCart where id = :id")
    fun find(id:Long) : ShoppingCart

    @Transaction
    @Query("select * from ShoppingCart where id = :id")
    fun findWithItems(id:Long) : List<ShoppingCartWithItems>
}