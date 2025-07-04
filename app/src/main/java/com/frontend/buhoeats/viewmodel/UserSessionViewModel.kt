package com.frontend.buhoeats.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModelProvider
import com.frontend.buhoeats.data.UserRepository
import com.frontend.buhoeats.data.InMemoryUserRepository
import com.frontend.buhoeats.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserSessionViewModel(
    private val userRepository: UserRepository = InMemoryUserRepository()
) : ViewModel() {
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
        userRepository.updateUser(user)
    }

    private val _users = mutableStateOf<List<User>>(emptyList())
    val users: State<List<User>> get() = _users

    fun loadUsers() {
        _users.value = userRepository.getUsers()
    }

    fun assignRoleToUser(email: String, newRole: String): Boolean {
        val result = userRepository.assignRoleToUser(email, newRole)
        loadUsers()
        return result
    }

    fun registerUser(newUser: User): Boolean {
        val result = userRepository.registerUser(newUser)
        loadUsers()
        return result
    }

    fun getUserByEmail(email: String): User? {
        return userRepository.getUserByEmail(email)
    }
}

class FavoritesViewModel(
    private val userSessionViewModel: UserSessionViewModel
) : ViewModel() {

    private val _favoriteRestaurantIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteRestaurantIds: StateFlow<Set<String>> = _favoriteRestaurantIds.asStateFlow()

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

    fun toggleFavorite(restaurantId: String) {
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
