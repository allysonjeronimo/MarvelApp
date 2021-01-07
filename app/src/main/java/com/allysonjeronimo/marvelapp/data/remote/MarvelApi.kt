package com.allysonjeronimo.marvelapp.data.remote

import com.allysonjeronimo.marvelapp.data.entity.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("comics")
    fun allComics(
        @Query("offset") offset:Int? = 0,
        @Query("ts") timeStamp:String,
        @Query("apikey") apiKey:String,
        @Query("hash") hash:String
    ) : Call<Response>

}