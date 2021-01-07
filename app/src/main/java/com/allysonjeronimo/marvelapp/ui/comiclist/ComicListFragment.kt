package com.allysonjeronimo.marvelapp.ui.comiclist

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.createViewModelLazy
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.allysonjeronimo.marvelapp.MainActivity
import com.allysonjeronimo.marvelapp.R
import com.allysonjeronimo.marvelapp.data.entity.Comic
import com.allysonjeronimo.marvelapp.data.remote.MarvelApi
import com.allysonjeronimo.marvelapp.extensions.navigateWithAnimations
import com.allysonjeronimo.marvelapp.repository.MarvelApiRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.comic_list_fragment.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ComicListFragment : Fragment(R.layout.comic_list_fragment) {

    private lateinit var viewModel:ComicListViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModel()
        observeEvents()
    }

    private fun createViewModel(){
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://gateway.marvel.com/v1/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val repository = MarvelApiRepository(retrofit.create(MarvelApi::class.java))

        viewModel = ViewModelProvider(
            this,
            ComicListViewModel.ComicListViewModelFactory(repository)
        ).get(ComicListViewModel::class.java)
    }

    private fun observeEvents(){
        viewModel.comicsLiveData().observe(this.viewLifecycleOwner, {
            comics -> recycler_view_comics.adapter = ComicListAdapter(comics, this::onClickListener)
        })
        viewModel.isLoadingsLiveData().observe(this.viewLifecycleOwner, {
            isLoading ->
            if(isLoading)
                showProgress()
            else
                hideProgress()

        })
        viewModel.errorMessageLiveData().observe(this.viewLifecycleOwner, {
            stringResource ->
            Snackbar.make(requireView(), stringResource, Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun onClickListener(comic: Comic){
        findNavController().navigateWithAnimations(R.id.comicDetailsFragment)
    }

    private fun hideProgress() {
        progress_bar.visibility = View.GONE
        recycler_view_comics.visibility = View.VISIBLE
    }

    private fun showProgress() {
        progress_bar.visibility = View.VISIBLE
        recycler_view_comics.visibility = View.GONE
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadComics()
    }

}