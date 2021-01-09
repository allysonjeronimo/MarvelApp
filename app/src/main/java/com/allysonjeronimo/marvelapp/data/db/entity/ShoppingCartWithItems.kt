package com.allysonjeronimo.marvelapp.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ShoppingCartWithItems(
    @Embedded
    val shoppingCart : ShoppingCart,
    @Relation(
        parentColumn = "id",
        entityColumn = "shoppingCartId"
    )
    val items:List<ShoppingCartItem>
)