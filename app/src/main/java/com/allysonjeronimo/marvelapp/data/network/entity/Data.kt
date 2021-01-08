package com.allysonjeronimo.marvelapp.data.network.entity

data class Data(
    val offset:Int,
    val limit:Int,
    val total:Int,
    val count:Int,
    val results:List<Comic>
)