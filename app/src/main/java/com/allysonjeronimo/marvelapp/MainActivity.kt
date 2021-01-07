package com.allysonjeronimo.marvelapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.allysonjeronimo.marvelapp.data.remote.MarvelApi
import com.allysonjeronimo.marvelapp.repository.MarvelApiRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        loadComics()
    }

    private fun loadComics(){

        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://gateway.marvel.com/v1/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val repository = MarvelApiRepository(retrofit.create(MarvelApi::class.java))

        repository.allComics({
            Log.i(MainActivity::class.simpleName, it.toString())
        }, {
            Log.i(MainActivity::class.simpleName, "Falhou miseravelmente!")
        })
    }
}