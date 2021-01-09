package com.allysonjeronimo.marvelapp.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartItem

@Dao
interface ShoppingCartItemDAO {
    @Insert
    suspend fun insert(shoppingCartItem: ShoppingCartItem) : Long

    @Update
    suspend fun update(shoppingCartItem: ShoppingCartItem)

    @Query("delete from ShoppingCartItem where id = :id")
    suspend fun delete(id:Long)

    @Query("select * from ShoppingCartItem where shoppingCartId = :shoppingCartId")
    suspend fun findByShoppingCart(shoppingCartId:Long) : List<ShoppingCartItem>
}