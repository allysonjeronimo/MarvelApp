package com.allysonjeronimo.marvelapp.ui.comicdetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.allysonjeronimo.marvelapp.R

class ComicDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ComicDetailsFragment()
    }

    private lateinit var viewModel: ComicDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.comic_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ComicDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}