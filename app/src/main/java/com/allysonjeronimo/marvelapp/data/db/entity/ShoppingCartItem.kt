package com.allysonjeronimo.marvelapp.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Comic::class,
            parentColumns = ["id"],
            childColumns = ["comicId"]
        ),
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
    val comicId:Int,
    val shoppingCartId:Long,
    val quantity:Int
)
