package com.allysonjeronimo.marvelapp.data.network

import com.allysonjeronimo.marvelapp.data.network.entity.ComicDataWrapper
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("comics")
    suspend fun allComics(
        @Query("offset") offset:Int? = 0,
        @Query("ts") timeStamp:String,
        @Query("apikey") apiKey:String,
        @Query("hash") hash:String
    ) : ComicDataWrapper

    @GET("comics/{comicId}")
    suspend fun findComic(
        @Path("comicId") comicId:Int,
        @Query("ts") timeStamp:String,
        @Query("apikey") apiKey:String,
        @Query("hash") hash:String
    ) : ComicDataWrapper

}