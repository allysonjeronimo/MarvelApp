package com.allysonjeronimo.marvelapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Comic(
    @PrimaryKey
    val id:Int,
    val title:String,
    val description:String?,
    val pageCount:Int,
    val thumbnailPath:String,
    val thumbnailExtension:String,
    val price:Double,
    val creators:String,
    val issueNumber:Double
)