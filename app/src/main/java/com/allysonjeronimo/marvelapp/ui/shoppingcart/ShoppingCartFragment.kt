package com.allysonjeronimo.marvelapp.ui.shoppingcart

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.allysonjeronimo.marvelapp.R

class ShoppingCartFragment : Fragment(R.layout.shopping_cart_fragment) {

    private lateinit var viewModel: ShoppingCartViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        createViewModel()
    }

    private fun createViewModel(){

    }

}