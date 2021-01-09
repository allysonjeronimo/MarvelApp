package com.allysonjeronimo.marvelapp.data.network.entity

data class ComicData(
    val id:Int,
    val title:String,
    val description:String?,
    val pageCount:Int,
    val thumbnail: Image,
    val prices:List<ComicPrice>,
    val creators:CreatorList,
    val issueNumber:Double,
){

    fun firstPrice() : Double{
        return prices[0].price
    }
}