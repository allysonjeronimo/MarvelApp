package com.allysonjeronimo.marvelapp.repository

import com.allysonjeronimo.marvelapp.data.db.entity.Comic
import com.allysonjeronimo.marvelapp.data.network.entity.ComicData
import com.allysonjeronimo.marvelapp.data.network.entity.ComicDataWrapper
import com.allysonjeronimo.marvelapp.data.network.MarvelApi
import com.allysonjeronimo.marvelapp.data.network.PRIVATE_KEY
import com.allysonjeronimo.marvelapp.data.network.PUBLIC_KEY
import com.allysonjeronimo.marvelapp.extensions.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import java.util.*

class ComicDataRepository(
    private val api: MarvelApi
) : ComicRepository {

    override suspend fun refreshComics() {
        withContext(Dispatchers.IO){

        }
    }

    override fun allComics(success:(List<Comic>) -> Unit, failure:() -> Unit){

        val timestamp = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
        val hash = "$timestamp$PRIVATE_KEY$PUBLIC_KEY".toMD5()
        val response = api.allComics(timeStamp = timestamp, apiKey = PUBLIC_KEY, hash = hash)

        response.enqueue(object : Callback<ComicDataWrapper> {
            override fun onResponse(
                call: Call<ComicDataWrapper>,
                response: retrofit2.Response<ComicDataWrapper>
            ) {
                if (response.isSuccessful) {
                    success(response.body()?.data?.getComicsAsModel() ?: listOf<Comic>())
                } else {
                    failure()
                }
            }

            override fun onFailure(call: Call<ComicDataWrapper>, t: Throwable) {
                failure()
            }
        })
    }

    override fun findComic(id: Int, success:(Comic?) -> Unit, failure:() -> Unit){
        val timestamp = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
        val hash = "$timestamp$PRIVATE_KEY$PUBLIC_KEY".toMD5()
        val response = api.findComic(comicId = id, timeStamp = timestamp, apiKey = PUBLIC_KEY, hash = hash)

        response.enqueue(object: Callback<ComicDataWrapper>{

            override fun onResponse(call: Call<ComicDataWrapper>, response: retrofit2.Response<ComicDataWrapper>) {
                if(response.isSuccessful && response.body()?.data?.results?.isNotEmpty() == true){
                    success(response.body()?.data?.getComicsAsModel()!![0])
                }
                else{
                    failure()
                }
            }

            override fun onFailure(call: Call<ComicDataWrapper>, t: Throwable) {
                failure()
            }
        })
    }
}