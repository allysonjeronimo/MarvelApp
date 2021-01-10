package com.allysonjeronimo.marvelapp.repository

import com.allysonjeronimo.marvelapp.data.db.entity.Comic
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartItem

interface MarvelRepository {

    suspend fun allComics() : List<Comic>

    suspend fun findComic(id:Int) : Comic?

    suspend fun addShoppingCartItem(shoppingCartItem: ShoppingCartItem)

    suspend fun deleteShoppingCartItem(shoppingCartItem: ShoppingCartItem)

    suspend fun allShoppingCartItems() : List<ShoppingCartItem>
}