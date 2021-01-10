package com.allysonjeronimo.marvelapp.ui.shoppingcart

import androidx.lifecycle.*
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartItem
import com.allysonjeronimo.marvelapp.repository.MarvelRepository
import kotlinx.coroutines.launch
import java.lang.Exception
import com.allysonjeronimo.marvelapp.R

class ShoppingCartViewModel(
    private val repository: MarvelRepository
) : ViewModel() {

    private val shoppingCartItemsLiveData = MutableLiveData<List<ShoppingCartItem>>()
    private val messageLiveData = MutableLiveData<Int>()

    fun shoppingCartItemsLiveData() = shoppingCartItemsLiveData as LiveData<List<ShoppingCartItem>>
    fun messageLiveData() = messageLiveData as LiveData<Int>

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

    fun checkAndApplyDiscount(code:String) = viewModelScope.launch {
        try{
            if(shoppingCartItemsLiveData.value?.isNotEmpty() == true){
                repository.checkAndApplyDiscount(code)
                loadItems()
            }
            else{
                messageLiveData.value = R.string.shopping_cart_text_empty
            }
        }catch(ex:Exception){
            messageLiveData.value = R.string.shopping_cart_error_invalid_discount_code
        }
    }

    fun processCheckout() = viewModelScope.launch {
        repository.processCheckout()
    }

    class ShoppingCartViewModelFactory(
        private val repository: MarvelRepository
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ShoppingCartViewModel(repository) as T
        }
    }

}