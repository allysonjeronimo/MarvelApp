package com.allysonjeronimo.marvelapp.ui.comiclist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.allysonjeronimo.marvelapp.R
import com.allysonjeronimo.marvelapp.data.db.AppDatabase
import com.allysonjeronimo.marvelapp.data.db.entity.Comic
import com.allysonjeronimo.marvelapp.data.network.BASE_URL
import com.allysonjeronimo.marvelapp.data.network.MarvelApi
import com.allysonjeronimo.marvelapp.extensions.navigateWithAnimations
import com.allysonjeronimo.marvelapp.repository.MarvelDataRepository
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

    /**
     * Será utilizado Injeção de Dependências com Koin
     */
    private fun createViewModel(){
        val api = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelApi::class.java)

        val database = AppDatabase.getInstance(requireContext())

        val repository = MarvelDataRepository(api, database)

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
        val bundle = bundleOf("comicId" to comic.id)
        findNavController().navigateWithAnimations(R.id.comicDetailsFragment, bundle)
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