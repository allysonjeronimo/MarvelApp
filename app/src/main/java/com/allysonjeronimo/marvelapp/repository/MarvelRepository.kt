package com.allysonjeronimo.marvelapp.repository

import com.allysonjeronimo.marvelapp.data.entity.Comic

interface MarvelRepository {

    fun allComics(success:(List<Comic>) -> Unit, failure:() -> Unit)

}