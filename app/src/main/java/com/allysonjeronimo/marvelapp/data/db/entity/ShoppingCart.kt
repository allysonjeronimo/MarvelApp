package com.allysonjeronimo.marvelapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingCart(
    @PrimaryKey(autoGenerate = true)
    val id:Long
)