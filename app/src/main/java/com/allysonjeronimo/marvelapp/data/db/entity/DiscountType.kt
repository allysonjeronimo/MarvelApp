package com.allysonjeronimo.marvelapp.data.db.entity

data class DiscountType(
    val id:Long = 0,
    val description:String,
    val rare:Boolean,
    val discount:Double = 0.0
)