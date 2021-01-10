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
    val quantity:Int,
    val value:Double,
    val discount:Double = 0.0
){
    fun subtotal() : Double{
        return value * quantity
    }

    fun subtotalWithDiscount() : Double{
        return (value-(value*discount)) * quantity
    }
}
