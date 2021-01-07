package com.allysonjeronimo.marvelapp.repository

import com.allysonjeronimo.marvelapp.data.entity.Comic
import com.allysonjeronimo.marvelapp.data.entity.Response
import com.allysonjeronimo.marvelapp.data.remote.MarvelApi
import com.allysonjeronimo.marvelapp.data.remote.PRIVATE_KEY
import com.allysonjeronimo.marvelapp.data.remote.PUBLIC_KEY
import com.allysonjeronimo.marvelapp.extensions.toMD5
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class MarvelApiRepository(
    private val api:MarvelApi
) : MarvelRepository{

    override fun allComics(success: (List<Comic>) -> Unit, failure: () -> Unit) {

        try{

            val timestamp = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
            val hash = "$timestamp$PRIVATE_KEY$PUBLIC_KEY".toMD5()
            val response = api.allComics(timeStamp = timestamp, apiKey = PUBLIC_KEY, hash = hash)

            response.enqueue(object: Callback<Response>{
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    if(response.isSuccessful){
                        success(response.body()?.data?.results ?: listOf<Comic>())
                    }
                    else{
                        failure()
                    }
                }

                override fun onFailure(call: Call<Response>, t: Throwable) {
                    failure()
                }
            })
        }catch(ex:Exception){
            ex.printStackTrace()
        }
    }
}