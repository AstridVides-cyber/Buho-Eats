package com.frontend.buhoeats.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.models.Promo
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

class PromoViewModel : ViewModel() {
    private val _promos = mutableStateListOf<Promo>()
    val promos: List<Promo> get() = _promos

    private var isLoaded = false

    fun loadPromos(promos: List<Promo>) {
        if (!isLoaded) {
            _promos.clear()
            _promos.addAll(promos)
            isLoaded = true
        }
    }

    fun deletePromo(promo: Promo) {
        _promos.remove(promo)
    }
    fun addPromo(promo: Promo) {
        _promos.add(promo)
    }
    fun updatePromo(updatedPromo: Promo) {
        val index = _promos.indexOfFirst { it.id == updatedPromo.id }
        if (index != -1) {
            _promos[index] = updatedPromo
        }
    }



}



