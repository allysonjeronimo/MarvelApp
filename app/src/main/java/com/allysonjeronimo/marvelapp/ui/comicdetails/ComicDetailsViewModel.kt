package com.allysonjeronimo.marvelapp.ui.comicdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ComicDetailsViewModel : ViewModel() {

    class ComicListViewModelFactory() : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ComicDetailsViewModel() as T
        }
    }
}