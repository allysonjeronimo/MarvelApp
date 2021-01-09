package com.allysonjeronimo.marvelapp.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ShoppingCart::class,
            parentColumns = ["id"],
            childColumns = ["shoppingCartId"],
            onDelete = CASCADE
        )
    ]
)
data class ShoppingCartItem(
    @PrimaryKey(autoGenerate = true)
    val id:Long,
    @Embedded(prefix = "comic_")
    val comic:Comic,
    val shoppingCartId:Long,
    val quantity:Int
){
    fun subtotal() : Double{
        return 0.0
    }
}
