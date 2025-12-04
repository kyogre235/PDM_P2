package com.example.pokedex.repositorio

import com.example.pokedex.db.ItemDao
import com.example.pokedex.lector.PokeApi
import com.example.pokedex.model.Item

class ItemRepository(private val itemDao: ItemDao) {

    private val pokeApi = PokeApi()

    suspend fun getItemList(forceRefresh: Boolean = false): List<Item> {
        val cachedItems = itemDao.getAllItems()
        if (cachedItems.isNotEmpty() && !forceRefresh) {
            return cachedItems
        }

        val newItems = pokeApi.getItemList()
        itemDao.insertAll(newItems)
        return newItems
    }

    suspend fun searchItem(query: String): List<Item> {
        return itemDao.searchItemByName("%${query}%")
    }
}
