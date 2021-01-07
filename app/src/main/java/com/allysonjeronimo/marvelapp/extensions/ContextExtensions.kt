package com.allysonjeronimo.marvelapp.extensions

import android.content.Context

fun Context.loadJSON(fileName:String) : String{
    return assets.open(fileName).bufferedReader().use{it.readLine()}
}