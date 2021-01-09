package com.allysonjeronimo.marvelapp.data.network.paging

import androidx.paging.PageKeyedDataSource
import com.allysonjeronimo.marvelapp.data.network.MarvelApi
import com.allysonjeronimo.marvelapp.data.network.entity.ComicData

class ComicsDataSource(
    private val api:MarvelApi
) : PageKeyedDataSource<Int, ComicData>(){

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ComicData>
    ) {
        TODO("Not yet implemented")
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ComicData>) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ComicData>) {
        TODO("Not yet implemented")
    }

}