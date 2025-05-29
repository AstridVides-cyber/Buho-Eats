package com.frontend.buhoeats.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.models.Restaurant
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "favorites")

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val context = application.applicationContext

    private val FAVORITES_KEY = stringSetPreferencesKey("favorite_ids")

    private val _favoriteRestaurantIds = MutableStateFlow<Set<String>>(setOf())
    val favoriteRestaurantIds: StateFlow<Set<String>> = _favoriteRestaurantIds.asStateFlow()

    init {
        viewModelScope.launch {
            context.dataStore.data
                .map { preferences ->
                    preferences[FAVORITES_KEY] ?: setOf()
                }
                .collect {
                    _favoriteRestaurantIds.value = it
                }
        }
    }

    fun toggleFavorite(restaurantId: Int) {
        val idAsString = restaurantId.toString()
        val current = _favoriteRestaurantIds.value
        val newSet = if (current.contains(idAsString)) {
            current - idAsString
        } else {
            current + idAsString
        }

        viewModelScope.launch {
            context.dataStore.edit { preferences ->
                preferences[FAVORITES_KEY] = newSet
            }
        }
    }

    fun isFavorite(restaurantId: Int): Boolean {
        return _favoriteRestaurantIds.value.contains(restaurantId.toString())
    }
}

