package com.frontend.buhoeats.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.models.User

class RestaurantViewModel : ViewModel() {
    private val _blockedUsers = mutableStateListOf<User>()
    val blockedUsers: List<User> get() = _blockedUsers

    fun loadBlockedUsers(restaurant: Restaurant) {
        _blockedUsers.clear()
        _blockedUsers.addAll(
            restaurant.blockedUsers.mapNotNull { userId ->
                DummyData.getUsers().find { it.id == userId }
            }
        )
    }

    fun unblockUser(user: User) {
        _blockedUsers.remove(user)
    }
}



