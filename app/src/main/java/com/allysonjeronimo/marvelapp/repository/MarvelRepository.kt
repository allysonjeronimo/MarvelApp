package com.allysonjeronimo.marvelapp.repository

import com.allysonjeronimo.marvelapp.data.network.entity.Comic

interface MarvelRepository {

    fun allComics(success:(List<Comic>) -> Unit, failure:() -> Unit)

    fun findComic(id:Int, success: (Comic?) -> Unit, failure: () -> Unit)
}