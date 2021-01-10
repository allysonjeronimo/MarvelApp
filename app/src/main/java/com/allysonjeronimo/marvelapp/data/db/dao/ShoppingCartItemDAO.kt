package com.allysonjeronimo.marvelapp.data.db.dao

import androidx.room.*
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartItem

@Dao
interface ShoppingCartItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(shoppingCartItem: ShoppingCartItem) : Long

    @Update
    suspend fun update(shoppingCartItem: ShoppingCartItem)

    @Query("update ShoppingCartItem set discount = :discount")
    suspend fun applyRareDiscount(discount:Double)

    @Query("update ShoppingCartItem set discount = :discount where comic_rare = 0")
    suspend fun applyCommonDiscount(discount:Double)

    @Query("delete from ShoppingCartItem where id = :id")
    suspend fun delete(id:Int)

    @Query("delete from ShoppingCartItem")
    suspend fun deleteAll()

    @Query("select * from ShoppingCartItem")
    suspend fun findAll() : List<ShoppingCartItem>
}