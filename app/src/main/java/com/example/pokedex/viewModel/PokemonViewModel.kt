package com.example.pokedex.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.Pokemon
import com.example.pokedex.repositorio.PokemonRepository
import kotlinx.coroutines.launch

class PokemonViewModel(private val repository: PokemonRepository) : ViewModel() {

    private val _pokemonList = MutableLiveData<List<Pokemon>>()
    val pokemonList: LiveData<List<Pokemon>> = _pokemonList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var isSearching = false

    fun loadPokemon(forceRefresh: Boolean = false) {
        if (isSearching) return

        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getPokemonList(forceRefresh)
            _pokemonList.value = result
            _isLoading.value = false
        }
    }

    fun searchPokemon(query: String) {
        if (query.isBlank()) {
            isSearching = false
            loadPokemon()
            return
        }

        isSearching = true
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.searchPokemon(query)
            _pokemonList.value = result
            _isLoading.value = false
        }
    }
}
