package com.allysonjeronimo.marvelapp.data.network.entity

data class CreatorSummary(
    val name:String,
    val role:String
){

    override fun toString() : String{
        return "$name ($role)"
    }
}