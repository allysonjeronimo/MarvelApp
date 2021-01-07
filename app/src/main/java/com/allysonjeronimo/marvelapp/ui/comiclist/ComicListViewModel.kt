package com.allysonjeronimo.marvelapp.ui.comiclist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.allysonjeronimo.marvelapp.R
import com.allysonjeronimo.marvelapp.data.entity.Comic
import com.allysonjeronimo.marvelapp.repository.MarvelRepository

class ComicListViewModel(
    private val repository : MarvelRepository
) : ViewModel() {

    private var comicsLiveData = MutableLiveData<List<Comic>>()
    private var isLoadingLiveData = MutableLiveData<Boolean>()
    private var errorMessageLiveData = MutableLiveData<Int>()

    fun comicsLiveData() = comicsLiveData as LiveData<List<Comic>>
    fun isLoadingsLiveData() = isLoadingLiveData as LiveData<Boolean>
    fun errorMessageLiveData() = errorMessageLiveData as LiveData<Int>

    fun loadComics(){
        isLoadingLiveData.value = true
        repository.allComics({ comics ->
            this.comicsLiveData.value = comics
            this.isLoadingLiveData.value = false
        }, {
            this.isLoadingLiveData.value = false
            this.errorMessageLiveData.value = R.string.comic_list_error_on_loading
        })
    }

    class ComicListViewModelFactory(
        private val repository: MarvelRepository
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ComicListViewModel(repository) as T
        }
    }
}