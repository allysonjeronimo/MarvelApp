package com.allysonjeronimo.marvelapp.data.network.entity

import com.allysonjeronimo.marvelapp.data.db.entity.Comic

data class ComicDataContainer(
    val offset:Int,
    val limit:Int,
    val total:Int,
    val count:Int,
    val results:List<ComicData>
){
    fun getComicsAsModel() : List<Comic>{
        return results.map {
            comicData -> Comic(
                id = comicData.id,
                title = comicData.title,
                description = comicData.description,
                pageCount = comicData.pageCount,
                thumbnailPath = comicData.thumbnail.path,
                thumbnailExtension = comicData.thumbnail.extension,
                price = comicData.firstPrice(),
                creators = comicData.creators.creatorsString(),
                issueNumber = comicData.issueNumber,
                rare = false
            )
        }
    }
}