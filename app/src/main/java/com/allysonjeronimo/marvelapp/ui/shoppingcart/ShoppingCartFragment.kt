package com.allysonjeronimo.marvelapp.ui.shoppingcart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
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

class ShoppingCartFragment : Fragment(R.layout.shopping_cart_fragment) {

    private lateinit var viewModel: ShoppingCartViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViewModel()
        observeEvents()
        setListeners()
    }

    /**
     * Será adicionado injeção de dependência utilizando Koin
     * para gerenciamento das dependências.
     */
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

    private fun showShoppingCartItems(items:List<ShoppingCartItem>){
        recycler_view_items.adapter = ShoppingCartItemAdapter(
            items,
            this::removeShoppingCartItem,
            this::showSelectedItem,
        )
        recycler_view_items.visibility = View.VISIBLE
        group_empty.visibility = View.GONE
    }

    private fun removeShoppingCartItem(item:ShoppingCartItem){
        viewModel.removeItem(item)
    }

    private fun showSelectedItem(item:ShoppingCartItem){
        val bundle = bundleOf("comicId" to item.comic.id)
        findNavController().navigateWithAnimations(R.id.comicDetailsFragment, bundle)
    }

    private fun hideShoppingCartItems(){
        recycler_view_items.visibility = View.GONE
        group_empty.visibility = View.VISIBLE
    }

    private fun showShoppingCartDetails(items:List<ShoppingCartItem>){
        val quantityItems = items.map { it.quantity }.sum()
        val subtotal = items.map { it.subtotal() }.sum().currencyFormat()
        text_subtotal_value.text = subtotal
        text_total.text = resources.getString(R.string.shopping_cart_total, quantityItems.toString())
        text_total_value.text = subtotal
        button_shopping_cart_action.text =
            if(viewModel.isEmpty())
                resources.getString(R.string.shopping_cart_continue_shopping)
            else
                resources.getString(R.string.shopping_cart_checkout)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setListeners() {

        button_shopping_cart_action.setOnClickListener {
            if(viewModel.isEmpty()){
                findNavController().navigateWithAnimations(R.id.comicListFragment)
            }
            else{
                // checkout!
            }
        }

        text_discount_code.addTextChangedListener {
            if(it.toString().trim().isNotEmpty()){
                val iconCheck = ResourcesCompat.getDrawable(resources, R.drawable.ic_check, null)
                text_discount_code.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, iconCheck, null)
            }
            else{
                text_discount_code.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
            }
        }

        text_discount_code.setOnEditorActionListener { _, _, _ ->
            checkDiscount()
            true
        }

        text_discount_code.setOnTouchListener { _, event ->
            val right = 2
            if(event.action == MotionEvent.ACTION_UP && text_discount_code.compoundDrawables[right] != null){
                if(event.rawX >= (text_discount_code.right - text_discount_code.compoundDrawables[right].bounds.width())){
                    checkDiscount()
                }
            }
            false
        }
    }

    private fun checkDiscount(){
        val discountCode = text_discount_code.text

    }

    override fun onStart() {
        super.onStart()
        viewModel.loadItems()
    }
}