package com.allysonjeronimo.marvelapp.data.entity

data class Comic(
    val id:Long,
    val title:String,
    val description:String,
    val format:String,
    val isbn:String,
    val pageCount:Int,
    val thumbnail: Thumbnail,
    val prices:List<Price>
)