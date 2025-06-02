package com.frontend.buhoeats.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.frontend.buhoeats.data.SearchHistoryManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchHistoryManager: SearchHistoryManager,
    private val userId: Int
) : ViewModel() {

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory: StateFlow<List<String>> = _searchHistory

    init {
        loadSearchHistory()
    }

    private fun loadSearchHistory() {
        viewModelScope.launch {
            searchHistoryManager.getSearchHistoryFlow(userId).collect { history ->
                _searchHistory.value = history
                    .filter { it.isNotBlank() && it != "Buscar" }
                    .distinct()
                    .take(10)
            }
        }
    }

    fun addToHistory(query: String) {
        viewModelScope.launch {
            if (query.isNotBlank() && query != "Buscar") {
                searchHistoryManager.removeSearchItem(userId, query)
                searchHistoryManager.addSearchItem(userId, query)
            }
        }
    }

    fun removeFromHistory(item: String) {
        viewModelScope.launch {
            searchHistoryManager.removeSearchItem(userId, item)
        }
    }
}

class SearchViewModelFactory(
    private val userId: Int,
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(
                searchHistoryManager = SearchHistoryManager(context),
                userId = userId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}