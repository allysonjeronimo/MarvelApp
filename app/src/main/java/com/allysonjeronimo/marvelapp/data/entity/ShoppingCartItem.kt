package com.allysonjeronimo.marvelapp.data.entity

import com.allysonjeronimo.marvelapp.data.network.entity.Comic

data class ShoppingCartItem(
    val comic:Comic,
    val quantity:Int
){
    fun subtotal() : Double{
        return comic.firstPrice() * quantity
    }
}