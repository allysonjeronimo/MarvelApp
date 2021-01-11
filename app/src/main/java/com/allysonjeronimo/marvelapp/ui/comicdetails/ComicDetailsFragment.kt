package com.allysonjeronimo.marvelapp.ui.comicdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.allysonjeronimo.marvelapp.R
import com.allysonjeronimo.marvelapp.data.db.AppDatabase
import com.allysonjeronimo.marvelapp.data.db.entity.Comic
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartItem
import com.allysonjeronimo.marvelapp.data.network.BASE_URL
import com.allysonjeronimo.marvelapp.data.network.MarvelApi
import com.allysonjeronimo.marvelapp.extensions.currencyFormat
import com.allysonjeronimo.marvelapp.extensions.load
import com.allysonjeronimo.marvelapp.extensions.navigateWithAnimations
import com.allysonjeronimo.marvelapp.repository.MarvelDataRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.comic_details_fragment.*
import kotlinx.android.synthetic.main.quantity_picker.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ComicDetailsFragment : Fragment(R.layout.comic_details_fragment) {

    private lateinit var viewModel: ComicDetailsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createViewModel()
        observeEvents()
        setListeners()
    }

    /**
     * Será utilizado Injeção de Dependências com Koin
     */
    private fun createViewModel() {
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
            ComicDetailsViewModel.ComicListViewModelFactory(repository)
        ).get(ComicDetailsViewModel::class.java)
    }

    private fun observeEvents() {
        viewModel.comicLiveData().observe(this.viewLifecycleOwner, {
                comic -> showComicDetails(comic)
        })
        viewModel.isLoadingsLiveData().observe(this.viewLifecycleOwner, {isLoading ->
            if(isLoading)
                showProgress()
            else
                hideProgress()
        })
        viewModel.errorMessageLiveData().observe(this.viewLifecycleOwner, {
                stringResource ->
            Snackbar.make(requireView(), stringResource, Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun showComicDetails(comic: Comic) {
        text_title.text = comic.title
        text_number.text = resources.getString(R.string.comic_details_number, comic.issueNumber.toString())
        text_pages.text = resources.getString(R.string.comic_details_pages, comic.pageCount.toString())
        text_creators.text = comic.creators
        text_summary.text = comic.description ?: resources.getString(R.string.comic_detauls_text_not_found)
        button_add.text = comic.price.currencyFormat().toString()
        image_detail_cover.load("${comic.thumbnailPath}/portrait_fantastic.${comic.thumbnailExtension}")
        if(comic.rare){
            view_rare.visibility = View.VISIBLE
        }
        else{
            view_rare.visibility = View.GONE
        }
    }

    private fun showProgress() {
        progress_bar.visibility = View.VISIBLE
        view_details.visibility = View.GONE
    }

    private fun hideProgress() {
        progress_bar.visibility = View.GONE
        view_details.visibility = View.VISIBLE
    }

    private fun setListeners() {
        button_add.setOnClickListener {
            val comic = viewModel.comic()
            if(comic != null){
                val item = ShoppingCartItem(comic.id, comic, text_quantity.text.toString().toInt(), comic.price)
                viewModel.addShoppingCartItem(item)
                findNavController().navigateWithAnimations(R.id.shoppingCartFragment)
            }
        }
        button_decrease.setOnClickListener {
            var value = text_quantity.text.toString().toInt()
            if(value > 1){
                value--
                text_quantity.setText(value.toString())
            }
        }
        button_increase.setOnClickListener {
            var value = text_quantity.text.toString().toInt()
            if(value < 10){
                value++
                text_quantity.setText(value.toString())
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadComic(arguments?.getInt("comicId") ?: 0)
    }

}