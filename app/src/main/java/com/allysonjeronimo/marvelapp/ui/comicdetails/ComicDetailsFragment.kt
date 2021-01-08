package com.allysonjeronimo.marvelapp.ui.comicdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.allysonjeronimo.marvelapp.R
import com.allysonjeronimo.marvelapp.data.entity.Comic
import com.allysonjeronimo.marvelapp.data.remote.BASE_URL
import com.allysonjeronimo.marvelapp.data.remote.MarvelApi
import com.allysonjeronimo.marvelapp.extensions.currencyFormat
import com.allysonjeronimo.marvelapp.extensions.load
import com.allysonjeronimo.marvelapp.repository.MarvelApiRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.comic_details_fragment.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ComicDetailsFragment : Fragment(R.layout.comic_details_fragment) {

    private lateinit var viewModel: ComicDetailsViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModel()
        observeEvents()
    }

    private fun createViewModel() {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val repository = MarvelApiRepository(retrofit.create(MarvelApi::class.java))

        viewModel = ViewModelProvider(
            this,
            ComicDetailsViewModel.ComicListViewModelFactory(repository)
        ).get(ComicDetailsViewModel::class.java)
    }

    private fun observeEvents() {
        viewModel.comicLiveData().observe(this.viewLifecycleOwner, {
            comic -> showComicDetails(comic)
        })
        viewModel.isLoadingsLiveData().observe(this.viewLifecycleOwner, {isLoading ->
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

    private fun hideProgress() {
        progress_bar.visibility = View.GONE
        view_details.visibility = View.VISIBLE
    }

    private fun showProgress() {
        progress_bar.visibility = View.VISIBLE
        view_details.visibility = View.GONE
    }

    private fun showComicDetails(comic: Comic) {
        text_title.text = comic.title
        text_number.text = resources.getString(R.string.comic_details_number, comic.issueNumber.toString())
        text_pages.text = resources.getString(R.string.comic_details_pages, comic.pageCount.toString())
        text_creators.text = comic.creators.creatorsString()
        text_summary.text = comic.description ?: resources.getString(R.string.comic_detauls_text_not_found)
        button_add.text = comic.firstPrice().currencyFormat().toString()
        image_detail_cover.load("${comic.thumbnail.path}/portrait_fantastic.${comic.thumbnail.extension}")
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadComic(arguments?.getInt("comicId") ?: 0)
    }


}