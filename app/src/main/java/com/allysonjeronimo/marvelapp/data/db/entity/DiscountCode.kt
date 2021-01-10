package com.allysonjeronimo.marvelapp.data.db.entity

data class DiscountCode(
    val id:Long = 0L,
    val code:String,
    val type:DiscountType
)