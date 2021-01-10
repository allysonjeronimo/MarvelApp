package com.allysonjeronimo.marvelapp.ui.shoppingcart

import android.content.ClipData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.allysonjeronimo.marvelapp.R
import com.allysonjeronimo.marvelapp.data.db.AppDatabase
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartItem
import com.allysonjeronimo.marvelapp.data.network.BASE_URL
import com.allysonjeronimo.marvelapp.data.network.MarvelApi
import com.allysonjeronimo.marvelapp.extensions.currencyFormat
import com.allysonjeronimo.marvelapp.extensions.navigateWithAnimations
import com.allysonjeronimo.marvelapp.repository.MarvelDataRepository
import kotlinx.android.synthetic.main.shopping_cart_fragment.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.concurrent.schedule

class ShoppingCartFragment : Fragment(R.layout.shopping_cart_fragment) {

    private lateinit var viewModel: ShoppingCartViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViewModel()
        observeEvents()
        setListeners()
    }

    private fun setListeners() {
        button_shopping_cart_action.setOnClickListener {
            if(viewModel.isEmpty()){
                findNavController().navigateWithAnimations(R.id.comicListFragment)
            }
            else{
                // checkout!
            }
        }
    }

    private fun createViewModel(){
        val api = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MarvelApi::class.java)

        val database = AppDatabase.getInstance(requireContext())

        val repository = MarvelDataRepository(api, database)

        viewModel = ViewModelProvider(
            this,
            ShoppingCartViewModel.ShoppingCartViewModelFactory(repository)
        ).get(ShoppingCartViewModel::class.java)
    }

    private fun observeEvents() {
        viewModel.shoppingCartItemsLiveData().observe(this.viewLifecycleOwner, {
            items ->
            if(items.isNotEmpty()){
                showShoppingCartItems(items)
            }
            else{
                hideShoppingCartItems()
            }
            showShoppingCartDetails(items)
        })
    }

    private fun removeShoppingCartItem(item:ShoppingCartItem){
        viewModel.removeItem(item)
    }

    private fun showShoppingCartDetails(items:List<ShoppingCartItem>){
        text_subtotal.text = resources.getString(R.string.shopping_cart_subtotal, items.size.toString())
        text_subtotal_value.text = items.map { it.subtotal() }.sum().currencyFormat()
        button_shopping_cart_action.text =
            if(viewModel.isEmpty())
                resources.getString(R.string.shopping_cart_continue_shopping)
            else
                resources.getString(R.string.shopping_cart_checkout)
    }

    private fun showShoppingCartItems(items:List<ShoppingCartItem>){
        recycler_view_items.adapter = ShoppingCartItemAdapter(
            items,
            this::removeShoppingCartItem
        )
        recycler_view_items.visibility = View.VISIBLE
        group_empty.visibility = View.GONE
    }

    private fun hideShoppingCartItems(){
        recycler_view_items.visibility = View.GONE
        group_empty.visibility = View.VISIBLE
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadItems()
    }
}