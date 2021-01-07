package com.allysonjeronimo.marvelapp.ui.comicdetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.allysonjeronimo.marvelapp.R

class ComicDetailsFragment : Fragment(R.layout.comic_details_fragment) {

    private lateinit var viewModel: ComicDetailsViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModel()
    }

    private fun createViewModel() {
        viewModel = ViewModelProvider(
            this,
            ComicDetailsViewModel.ComicListViewModelFactory()
        ).get(ComicDetailsViewModel::class.java)
    }

}