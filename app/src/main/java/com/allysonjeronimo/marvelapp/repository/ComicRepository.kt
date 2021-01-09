package com.allysonjeronimo.marvelapp.repository

import com.allysonjeronimo.marvelapp.data.db.entity.Comic

interface ComicRepository {

    suspend fun refreshComics()

    fun allComics(success:(List<Comic>) -> Unit, failure:() -> Unit)

    fun findComic(id:Int, success: (Comic?) -> Unit, failure: () -> Unit)
}