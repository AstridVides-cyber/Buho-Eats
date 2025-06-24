package com.frontend.buhoeats.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModelProvider
import com.frontend.buhoeats.data.InMemoryUserDataSource
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

        val userList = InMemoryUserDataSource.getUsers().toMutableList()
        val userIndex = InMemoryUserDataSource.getUsers().indexOfFirst { it.id == user.id }

        if (userIndex != -1) {
            userList[userIndex] = user
            InMemoryUserDataSource.setUsers(userList)
        }
    }
    private val _users = mutableStateOf<List<User>>(emptyList())
    val users: State<List<User>> get() = _users

    fun loadUsers() {
        _users.value = InMemoryUserDataSource.getUsers()
    }

    fun assignRoleToUser(email: String, newRole: String): Boolean {
        loadUsers()
        val index = _users.value.indexOfFirst { it.email.trim().equals(email.trim(), ignoreCase = true) }
        if (index != -1) {
            val updatedUser = _users.value[index].copy(rol = newRole)
            val updatedList = _users.value.toMutableList().apply { this[index] = updatedUser }
            _users.value = updatedList
            InMemoryUserDataSource.setUsers(updatedList)
            return true
        }
        return false
    }


    fun registerUser(newUser: User): Boolean {
        val users = InMemoryUserDataSource.getUsers().toMutableList()

        if (users.any { it.email.equals(newUser.email, ignoreCase = true) }) {
            return false
        }

        users.add(newUser)
        InMemoryUserDataSource.setUsers(users)
        return true
    }
    fun getUserByEmail(email: String): User? {
        return InMemoryUserDataSource.getUsers().find { it.email.equals(email, ignoreCase = true) }
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

