package com.allysonjeronimo.marvelapp.repository

import com.allysonjeronimo.marvelapp.data.db.AppDatabase
import com.allysonjeronimo.marvelapp.data.db.dao.ComicDAO
import com.allysonjeronimo.marvelapp.data.db.entity.Comic
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartItem
import com.allysonjeronimo.marvelapp.data.network.MarvelApi
import com.allysonjeronimo.marvelapp.data.network.PRIVATE_KEY
import com.allysonjeronimo.marvelapp.data.network.PUBLIC_KEY
import com.allysonjeronimo.marvelapp.extensions.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class MarvelDataRepository(
    private val api: MarvelApi,
    private val database: AppDatabase
) : MarvelRepository {

    private val comicDAO = database.ComicDAO()
    private val shoppingCartItemDAO = database.ShoppingCartItemDAO()

    private suspend fun refreshComics() {
        withContext(Dispatchers.IO){
            val timestamp = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
            val hash = "$timestamp$PRIVATE_KEY$PUBLIC_KEY".toMD5()

            val comics = api.allComics(
                timeStamp = timestamp,
                apiKey = PUBLIC_KEY,
                hash = hash)
                .data.getComicsAsModel()

            comicDAO.insertAll(comics)
        }
    }

    override suspend fun allComics(): List<Comic> {
        // load from api (connected? empty dabase? or update interval
        if(comicDAO.count() == 0){
            refreshComics()
        }
        return comicDAO.findAll()
    }

    override suspend fun findComic(id: Int): Comic? {
        return comicDAO.find(id)
    }

    override suspend fun addShoppingCartItem(shoppingCartItem: ShoppingCartItem) {
        shoppingCartItemDAO.insert(shoppingCartItem)
    }

    override suspend fun deleteShoppingCartItem(shoppingCartItem: ShoppingCartItem) {
        shoppingCartItemDAO.delete(shoppingCartItem.id)
    }

    override suspend fun allShoppingCartItems(): List<ShoppingCartItem> {
        return shoppingCartItemDAO.findAll()
    }
}