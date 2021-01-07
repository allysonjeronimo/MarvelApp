package com.allysonjeronimo.marvelapp.extensions

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.allysonjeronimo.marvelapp.R

/**
 * Permite realizar navegação entre Fragments
 * com animações de transição.
 */

private val slideLeftOptions = NavOptions.Builder()
    .setEnterAnim(R.anim.slide_in_right)
    .setExitAnim(R.anim.slide_out_left)
    .setPopEnterAnim(R.anim.slide_in_left)
    .setPopExitAnim(R.anim.slide_out_right)
    .build()

fun NavController.navigateWithAnimations(
    destinationId:Int,
    animation: NavOptions = slideLeftOptions
){
    this.navigate(destinationId, null, animation)
}