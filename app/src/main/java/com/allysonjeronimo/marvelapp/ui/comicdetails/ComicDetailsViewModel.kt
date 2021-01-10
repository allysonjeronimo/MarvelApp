package com.allysonjeronimo.marvelapp.ui.comicdetails

import androidx.lifecycle.*
import com.allysonjeronimo.marvelapp.R
import com.allysonjeronimo.marvelapp.data.db.entity.Comic
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartItem
import com.allysonjeronimo.marvelapp.repository.MarvelRepository
import kotlinx.coroutines.launch

class ComicDetailsViewModel(
    private val repository: MarvelRepository
) : ViewModel() {

    private val comicLiveData = MutableLiveData<Comic>()
    private var isLoadingLiveData = MutableLiveData<Boolean>()
    private var errorMessageLiveData = MutableLiveData<Int>()

    fun comicLiveData() = comicLiveData as LiveData<Comic>
    fun isLoadingsLiveData() = isLoadingLiveData as LiveData<Boolean>
    fun errorMessageLiveData() = errorMessageLiveData as LiveData<Int>
    fun comic() = comicLiveData.value

    fun addShoppingCartItem(shoppingCartItem: ShoppingCartItem) = viewModelScope.launch {
        try{
            isLoadingLiveData.value = true
            repository.addShoppingCartItem(shoppingCartItem)
            isLoadingLiveData.value = false
        }catch(ex: Exception){
            isLoadingLiveData.value = false
            errorMessageLiveData.value = R.string.comic_details_error_on_add
        }
    }

    fun loadComic(comicId:Int) = viewModelScope.launch{
        try{
            isLoadingLiveData.value = true
            val comic = repository.findComic(comicId)
            if(comic != null){
                comicLiveData.value = comic
            }
            else{
                errorMessageLiveData.value = R.string.comic_details_error_not_found
            }
            isLoadingLiveData.value = false
        }catch(ex:Exception){
            isLoadingLiveData.value = false
            errorMessageLiveData.value = R.string.comic_details_error_on_loading
        }
    }

    class ComicListViewModelFactory(private val repository: MarvelRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ComicDetailsViewModel(repository) as T
        }
    }
}