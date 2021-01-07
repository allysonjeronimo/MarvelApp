package com.allysonjeronimo.marvelapp.data.entity

data class Data(
    val offset:Int,
    val limit:Int,
    val total:Int,
    val count:Int,
    val results:List<Comic>
)