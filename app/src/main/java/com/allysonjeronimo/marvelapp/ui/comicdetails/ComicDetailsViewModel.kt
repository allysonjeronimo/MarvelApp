package com.allysonjeronimo.marvelapp.ui.comicdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.allysonjeronimo.marvelapp.R
import com.allysonjeronimo.marvelapp.data.network.entity.ComicData
import com.allysonjeronimo.marvelapp.repository.MarvelRepository

class ComicDetailsViewModel(
    private val repository: MarvelRepository
) : ViewModel() {

    private val comicLiveData = MutableLiveData<ComicData>()
    private var isLoadingLiveData = MutableLiveData<Boolean>()
    private var errorMessageLiveData = MutableLiveData<Int>()

    fun comicLiveData() = comicLiveData as LiveData<ComicData>
    fun isLoadingsLiveData() = isLoadingLiveData as LiveData<Boolean>
    fun errorMessageLiveData() = errorMessageLiveData as LiveData<Int>
    fun comic() = comicLiveData.value

    fun loadComic(comicId:Int){
        isLoadingLiveData.value = true
        repository.findComic(comicId, { comic ->
            isLoadingLiveData.value = false
            if(comic != null){
                comicLiveData.value = comic
            }
            else{
                errorMessageLiveData.value = R.string.comic_details_error_not_found
            }
        }, {
            isLoadingLiveData.value = false
            errorMessageLiveData.value = R.string.comic_details_error_on_loading
        })
    }

    class ComicListViewModelFactory(private val repository: MarvelRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ComicDetailsViewModel(repository) as T
        }
    }
}