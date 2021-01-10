package com.allysonjeronimo.marvelapp.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity
data class ShoppingCartItem(
    @PrimaryKey
    val id:Int,
    @Embedded(prefix = "comic_")
    val comic:Comic,
    val quantity:Int
){
    fun subtotal() : Double{
        return comic.price * quantity
    }
}
