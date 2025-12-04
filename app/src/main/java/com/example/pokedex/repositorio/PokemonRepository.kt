package com.example.pokedex.repositorio

import com.example.pokedex.model.Pokemon
import com.example.pokedex.db.PokemonDao
import com.example.pokedex.lector.PokeApi

class PokemonRepository(private val pokemonDao: PokemonDao) {

    private val pokeApi = PokeApi()

    suspend fun getPokemonList(forceRefresh: Boolean = false): List<Pokemon> {
        val cachedPokemon = pokemonDao.getAllPokemon()
        if (cachedPokemon.isNotEmpty() && !forceRefresh) {
            return cachedPokemon
        }

        val newPokemon = pokeApi.getPokemonList()
        pokemonDao.insertAll(newPokemon)
        return newPokemon
    }

    suspend fun searchPokemon(query: String): List<Pokemon> {
        return pokemonDao.searchPokemonByName("%${query}%")
    }
}
