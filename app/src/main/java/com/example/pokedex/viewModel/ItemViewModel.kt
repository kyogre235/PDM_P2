package com.example.pokedex.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.Item
import com.example.pokedex.repositorio.ItemRepository
import kotlinx.coroutines.launch

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {

    private val _itemList = MutableLiveData<List<Item>>()
    val itemList: LiveData<List<Item>> = _itemList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var isSearching = false

    fun loadItems(forceRefresh: Boolean = false) {
        if (isSearching) return

        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getItemList(forceRefresh)
            _itemList.value = result
            _isLoading.value = false
        }
    }

    fun searchItem(query: String) {
        if (query.isBlank()) {
            isSearching = false
            loadItems()
            return
        }

        isSearching = true
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.searchItem(query)
            _itemList.value = result
            _isLoading.value = false
        }
    }
}
