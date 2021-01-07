package com.allysonjeronimo.marvelapp.extensions

import android.widget.ImageView
import com.allysonjeronimo.marvelapp.R
import com.squareup.picasso.Picasso

fun ImageView.load(url:String){
    Picasso.get()
        .load(url.replace("http", "https"))
        .placeholder(R.drawable.placeholder)
        .into(this)
}