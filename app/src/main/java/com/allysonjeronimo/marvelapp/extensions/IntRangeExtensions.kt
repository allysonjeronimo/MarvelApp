package com.allysonjeronimo.marvelapp.extensions

import java.util.*

fun IntRange.random() =
    Random().nextInt((endInclusive + 1) - start) + start

fun IntRange.randomDistincts(n:Int) : List<Int> {
    val numbers = mutableListOf<Int>()
    while(numbers.size < n && numbers.size < (endInclusive - start + 1)){
        val next = (start..endInclusive).random()
        if(numbers.indexOf(next) == -1){
            numbers.add(next)
        }
    }
    return numbers
}