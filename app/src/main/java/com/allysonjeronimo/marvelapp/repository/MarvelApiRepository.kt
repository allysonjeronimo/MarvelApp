package com.allysonjeronimo.marvelapp.repository

import com.allysonjeronimo.marvelapp.data.network.entity.Comic
import com.allysonjeronimo.marvelapp.data.network.entity.Response
import com.allysonjeronimo.marvelapp.data.network.MarvelApi
import com.allysonjeronimo.marvelapp.data.network.PRIVATE_KEY
import com.allysonjeronimo.marvelapp.data.network.PUBLIC_KEY
import com.allysonjeronimo.marvelapp.extensions.toMD5
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class MarvelApiRepository(
    private val api: MarvelApi
) : MarvelRepository {

    override fun allComics(success: (List<Comic>) -> Unit, failure: () -> Unit) {

        val timestamp = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
        val hash = "$timestamp$PRIVATE_KEY$PUBLIC_KEY".toMD5()
        val response = api.allComics(timeStamp = timestamp, apiKey = PUBLIC_KEY, hash = hash)

        response.enqueue(object : Callback<Response> {
            override fun onResponse(
                call: Call<Response>,
                response: retrofit2.Response<Response>
            ) {
                if (response.isSuccessful) {
                    success(response.body()?.data?.results ?: listOf<Comic>())
                } else {
                    failure()
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                failure()
            }
        })
    }

    override fun findComic(id: Int, success: (Comic?) -> Unit, failure: () -> Unit) {

        val timestamp = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
        val hash = "$timestamp$PRIVATE_KEY$PUBLIC_KEY".toMD5()
        val response = api.findComic(comicId = id, timeStamp = timestamp, apiKey = PUBLIC_KEY, hash = hash)

        response.enqueue(object: Callback<Response>{

            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                if(response.isSuccessful && response.body()?.data?.results?.isNotEmpty() == true){
                    success(response.body()?.data?.results?.get(0))
                }
                else{
                    failure()
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {
                failure()
            }
        })
    }
}