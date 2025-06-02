package com.frontend.buhoeats.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.State
import com.frontend.buhoeats.models.User

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
}
