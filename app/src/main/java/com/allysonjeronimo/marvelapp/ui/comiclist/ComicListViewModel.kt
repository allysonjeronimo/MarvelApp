package com.allysonjeronimo.marvelapp.ui.comiclist

import androidx.lifecycle.*
import com.allysonjeronimo.marvelapp.R
import com.allysonjeronimo.marvelapp.data.db.entity.Comic
import com.allysonjeronimo.marvelapp.repository.MarvelRepository
import kotlinx.coroutines.launch

class ComicListViewModel(
    private val repository : MarvelRepository
) : ViewModel() {

    private var comicsLiveData = MutableLiveData<List<Comic>>()
    private var isLoadingLiveData = MutableLiveData<Boolean>()
    private var errorMessageLiveData = MutableLiveData<Int>()

    fun comicsLiveData() = comicsLiveData as LiveData<List<Comic>>
    fun isLoadingsLiveData() = isLoadingLiveData as LiveData<Boolean>
    fun errorMessageLiveData() = errorMessageLiveData as LiveData<Int>

    fun loadComics()  = viewModelScope.launch{
        try{
            isLoadingLiveData.value = true
            comicsLiveData.value = repository.allComics()
            isLoadingLiveData.value = false
        }catch(ex: Exception){
            errorMessageLiveData.value = R.string.comic_list_error_on_loading
        }
    }

    class ComicListViewModelFactory(
        private val repository: MarvelRepository
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ComicListViewModel(repository) as T
        }
    }
}