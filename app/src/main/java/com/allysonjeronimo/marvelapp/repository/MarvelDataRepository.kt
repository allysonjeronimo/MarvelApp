package com.allysonjeronimo.marvelapp.repository

import com.allysonjeronimo.marvelapp.data.db.AppDatabase
import com.allysonjeronimo.marvelapp.data.db.entity.Comic
import com.allysonjeronimo.marvelapp.data.db.entity.DiscountCode
import com.allysonjeronimo.marvelapp.data.db.entity.DiscountType
import com.allysonjeronimo.marvelapp.data.db.entity.ShoppingCartItem
import com.allysonjeronimo.marvelapp.data.network.MarvelApi
import com.allysonjeronimo.marvelapp.data.network.PRIVATE_KEY
import com.allysonjeronimo.marvelapp.data.network.PUBLIC_KEY
import com.allysonjeronimo.marvelapp.extensions.randomDistincts
import com.allysonjeronimo.marvelapp.extensions.toMD5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Algumas operações são delegadas para os respectivos DAOs
 * ou para o service da api da Marvel. No entanto, as operações
 * de definiação de quais quadrinhos são considerados raros bem
 * como a consulta dos códigos de desconto são implementadas em
 * métodos privados que simulam a lógica.
 */
class MarvelDataRepository(
    private val api: MarvelApi,
    private val database: AppDatabase
) : MarvelRepository {

    private val comicDAO = database.ComicDAO()
    private val shoppingCartItemDAO = database.ShoppingCartItemDAO()

    /**
     * Mocks de Cupons de Desconto
     */
    private val discountCodes = listOf<DiscountCode>(
        DiscountCode(1, "123456", DiscountType(1, "Common", false, 0.1)),
        DiscountCode(2, "654321", DiscountType(2, "Rare", true, 0.25))
    )

    /**
     * Define randomicamente quais as comics
     * serão consideradas raras (12% das comics)
     */
    private fun generateRandomRareComics(comics: List<Comic>){
        val numbers = (comics.indices).randomDistincts((0.12*comics.size).toInt())
        comics.forEachIndexed{ index, comic ->
            if(numbers.indexOf(index) != -1){
                comic.rare = true
            }
        }
    }

    private suspend fun refreshComics() {
        withContext(Dispatchers.IO){
            val timestamp = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
            val hash = "$timestamp$PRIVATE_KEY$PUBLIC_KEY".toMD5()

            val comics = api.allComics(
                timeStamp = timestamp,
                apiKey = PUBLIC_KEY,
                hash = hash
            ).data.getComicsAsModel()

            generateRandomRareComics(comics)

            comicDAO.insertAll(comics)
        }
    }

    /**
     * Na primeira consulta é realizado
     * um cache local das comics.
     * A estratégia de cache ainda será melhorada
     * levando em consideração fatores como disponibilidade
     * de conexão, data/hora da última atualização, paginação, etc
     */
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

    override suspend fun checkAndApplyDiscount(code: String) {
        val discountCode = discountCodes.find { it -> it.code == code }
        if(discountCode != null){
            if(discountCode.type.rare)
                shoppingCartItemDAO.applyRareDiscount(discountCode.type.discount)
            else
                shoppingCartItemDAO.applyCommonDiscount(discountCode.type.discount)
        }
        else{
            throw IllegalArgumentException()
        }
    }

    /**
     * Não é gerado um pedido de fato, apenas
     * são removidos os itens criados temporariamente
     * no "carrinho de compras".
     */
    override suspend fun processCheckout() {
        shoppingCartItemDAO.deleteAll()
    }

}