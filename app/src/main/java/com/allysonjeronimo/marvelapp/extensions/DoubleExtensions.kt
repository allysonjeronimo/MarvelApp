package com.allysonjeronimo.marvelapp.extensions

import java.text.NumberFormat
import java.util.*

fun Double.currencyFormat() : String {
    val formatter = NumberFormat.getCurrencyInstance()
    formatter.currency = Currency.getInstance("USD")
    return formatter.format(this)
}