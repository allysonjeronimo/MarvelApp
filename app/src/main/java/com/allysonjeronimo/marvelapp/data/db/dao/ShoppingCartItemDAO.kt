package com.allysonjeronimo.marvelapp.data.db.dao

import androidx.room.*
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartItem

@Dao
interface ShoppingCartItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shoppingCartItem: ShoppingCartItem) : Long

    @Update
    suspend fun update(shoppingCartItem: ShoppingCartItem)

    @Query("delete from ShoppingCartItem where id = :id")
    suspend fun delete(id:Int)

    @Query("select * from ShoppingCartItem")
    suspend fun findAll() : List<ShoppingCartItem>
}