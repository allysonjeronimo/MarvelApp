package com.allysonjeronimo.marvelapp.data.entity

data class CreatorList (
    val items:List<CreatorSummary>
){

    fun creatorsString() : String {
        return items.joinToString()
    }
}
