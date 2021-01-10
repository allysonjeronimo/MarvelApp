package com.allysonjeronimo.marvelapp.ui.checkout

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.allysonjeronimo.marvelapp.R
import com.allysonjeronimo.marvelapp.extensions.navigateWithAnimations
import com.allysonjeronimo.marvelapp.extensions.random
import kotlinx.android.synthetic.main.checkout_fragment.*

class CheckoutFragment : Fragment(R.layout.checkout_fragment) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        showDetails()
        setListeners()
    }

    private fun showDetails(){
        text_order_number.text = resources.getString(R.string.checkout_order_number, (1..10000).random().toString())
    }

    private fun setListeners() {
        button_back.setOnClickListener {
            findNavController().navigateWithAnimations(R.id.comicListFragment)
        }
    }
}