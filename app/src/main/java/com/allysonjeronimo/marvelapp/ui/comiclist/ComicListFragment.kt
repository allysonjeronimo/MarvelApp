package com.allysonjeronimo.marvelapp.ui.comiclist

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.allysonjeronimo.marvelapp.MainActivity
import com.allysonjeronimo.marvelapp.R
import com.allysonjeronimo.marvelapp.data.remote.MarvelApi
import com.allysonjeronimo.marvelapp.repository.MarvelApiRepository
import kotlinx.android.synthetic.main.comic_list_fragment.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ComicListFragment : Fragment(R.layout.comic_list_fragment) {

    private lateinit var viewModel: ComicListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view_comics.layoutManager = GridLayoutManager(requireContext(), 2)
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
            recycler_view_comics.adapter = ComicListAdapter(it)
        }, {
            Log.i(MainActivity::class.simpleName, "Falhou miseravelmente!")
        })
    }

}