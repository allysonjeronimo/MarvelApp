package com.allysonjeronimo.marvelapp.repository

import com.allysonjeronimo.marvelapp.data.network.entity.ComicData

interface MarvelRepository {

    fun allComics(success:(List<ComicData>) -> Unit, failure:() -> Unit)

    fun findComic(id:Int, success: (ComicData?) -> Unit, failure: () -> Unit)
}