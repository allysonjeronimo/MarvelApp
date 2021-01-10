package com.allysonjeronimo.marvelapp.ui.shoppingcart

import androidx.lifecycle.*
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartItem
import com.allysonjeronimo.marvelapp.extensions.notifyObserver
import com.allysonjeronimo.marvelapp.repository.MarvelRepository
import kotlinx.coroutines.launch

class ShoppingCartViewModel(
    private val repository: MarvelRepository
) : ViewModel() {

    private val shoppingCartItemsLiveData = MutableLiveData<List<ShoppingCartItem>>()

    fun shoppingCartItemsLiveData() = shoppingCartItemsLiveData as LiveData<List<ShoppingCartItem>>

    fun isEmpty(): Boolean{
        return shoppingCartItemsLiveData.value?.isEmpty() ?: true
    }

    fun loadItems() = viewModelScope.launch{
        shoppingCartItemsLiveData.value = repository.allShoppingCartItems()
    }

    fun removeItem(item:ShoppingCartItem) = viewModelScope.launch {
        repository.deleteShoppingCartItem(item)
        loadItems()
    }

    class ShoppingCartViewModelFactory(
        private val repository: MarvelRepository
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ShoppingCartViewModel(repository) as T
        }
    }

}