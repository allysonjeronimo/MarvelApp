package com.allysonjeronimo.marvelapp.data.db.dao

import androidx.room.*
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartItem

@Dao
interface ShoppingCartItemDAO {
    @Insert
    fun insert(shoppingCartItem: ShoppingCartItem) : Long

    @Update
    fun update(shoppingCartItem: ShoppingCartItem)

    @Query("delete from ShoppingCartItem where id = :id")
    fun delete(id:Long)

    @Query("select * from ShoppingCartItem where shoppingCartId = :shoppingCartId")
    fun findByShoppingCart(shoppingCartId:Long) : List<ShoppingCartItem>
}