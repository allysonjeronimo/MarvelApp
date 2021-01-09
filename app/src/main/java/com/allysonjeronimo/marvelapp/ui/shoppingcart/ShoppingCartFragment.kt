package com.allysonjeronimo.marvelapp.ui.shoppingcart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.allysonjeronimo.marvelapp.R
import kotlinx.android.synthetic.main.shopping_cart_fragment.*

class ShoppingCartFragment : Fragment(R.layout.shopping_cart_fragment) {

    private lateinit var viewModel: ShoppingCartViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViewModel()
        observeEvents()
    }

    private fun createViewModel(){
        viewModel = ViewModelProvider(
            this,
            ShoppingCartViewModel.ShoppingCartViewModelFactory()
        ).get(ShoppingCartViewModel::class.java)
    }

    private fun observeEvents() {
        viewModel.shoppingCartItemsLiveData().observe(this.viewLifecycleOwner, {
            items -> recycler_view_items.adapter = ShoppingCartItemAdapter(items)
        })
    }
}