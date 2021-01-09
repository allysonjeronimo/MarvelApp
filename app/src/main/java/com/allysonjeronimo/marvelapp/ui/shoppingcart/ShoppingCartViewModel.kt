package com.allysonjeronimo.marvelapp.ui.shoppingcart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartItem
import com.allysonjeronimo.marvelapp.extensions.notifyObserver

class ShoppingCartViewModel : ViewModel() {

    private val shoppingCartItemsLiveData = MutableLiveData<List<ShoppingCartItem>>()

    fun shoppingCartItemsLiveData() = shoppingCartItemsLiveData as LiveData<List<ShoppingCartItem>>

    fun addItem(shoppingCartItem: ShoppingCartItem){
        shoppingCartItemsLiveData.value?.toMutableList()?.add(shoppingCartItem)
        shoppingCartItemsLiveData.notifyObserver()
    }

    class ShoppingCartViewModelFactory : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ShoppingCartViewModel() as T
        }
    }

}