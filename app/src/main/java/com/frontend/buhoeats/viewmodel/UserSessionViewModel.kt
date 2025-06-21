package com.frontend.buhoeats.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModelProvider
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserSessionViewModel : ViewModel() {
    private val _currentUser = mutableStateOf<User?>(null)
    val currentUser: State<User?> get() = _currentUser

    fun login(user: User) {
        _currentUser.value = user
    }

    fun logout() {
        _currentUser.value = null
    }

    fun updateCurrentUser(user: User) {
        _currentUser.value = user
    }
    fun assignRoleToUser(email: String, newRole: String): Boolean {
        val userIndex = DummyData.getUsers().indexOfFirst { it.email.equals(email, ignoreCase = true) }

        if (userIndex != -1) {
            val userList = DummyData.getUsers().toMutableList()
            val user = userList[userIndex]
            val updatedUser = user.copy(rol = newRole)
            userList[userIndex] = updatedUser

            println("Usuario actualizado: ${updatedUser.email} -> ${updatedUser.rol}")
            return true
        }
        return false
    }


}

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

