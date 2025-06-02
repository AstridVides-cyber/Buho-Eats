package com.frontend.buhoeats.viewmodel

import kotlinx.coroutines.flow.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavoritesViewModel(
    private val userSessionViewModel: UserSessionViewModel
) : ViewModel() {

    private val _favoriteRestaurantIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteRestaurantIds: StateFlow<Set<Int>> = _favoriteRestaurantIds.asStateFlow()

    init {
        userSessionViewModel.currentUser.value?.favoritos?.let { favs ->
            _favoriteRestaurantIds.value = favs.toSet()
        }
    }

    fun refreshFavorites() {
        userSessionViewModel.currentUser.value?.favoritos?.let { favs ->
            _favoriteRestaurantIds.value = favs.toSet()
        }
    }

    fun toggleFavorite(restaurantId: Int) {
        val currentUser = userSessionViewModel.currentUser.value ?: return
        val current = _favoriteRestaurantIds.value.toMutableSet()

        if (current.contains(restaurantId)) {
            current.remove(restaurantId)
            currentUser.favoritos.remove(restaurantId)
        } else {
            current.add(restaurantId)
            currentUser.favoritos.add(restaurantId)
        }

        _favoriteRestaurantIds.value = current
        userSessionViewModel.updateCurrentUser(currentUser)
    }
}

class FavoritesViewModelFactory(
    private val userSessionViewModel: UserSessionViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(userSessionViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


