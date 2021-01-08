package com.allysonjeronimo.marvelapp.data.entity

data class Comic(
    val id:Int,
    val title:String,
    val description:String?,
    val format:String,
    val isbn:String,
    val pageCount:Int,
    val thumbnail: Thumbnail,
    val prices:List<Price>,
    val creators:CreatorList,
    val issueNumber:String,
){

    fun firstPrice() : Double{
        return prices[0].price
    }
}