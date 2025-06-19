package com.frontend.buhoeats.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.models.Promo
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.models.User

class RestaurantViewModel : ViewModel() {

    fun addRestaurant(restaurant: Restaurant) {
        DummyData.addRestaurant(restaurant)
    }

    fun updateRestaurant(updated: Restaurant) {
        DummyData.updateRestaurant(updated)
    }

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

    fun blockUser(user: User, restaurant: Restaurant, onUpdate: (Restaurant) -> Unit) {
        if (!_blockedUsers.contains(user)) {
            _blockedUsers.add(user)

            val updatedComments = restaurant.comments.filterNot { it.userId == user.id }.toMutableList()
            val updatedRatings = restaurant.ratings.filterNot { it.userId == user.id }.toMutableList()

            val updatedRestaurant = restaurant.copy(
                comments = updatedComments,
                ratings = updatedRatings,
                blockedUsers = restaurant.blockedUsers + user.id
            )

            onUpdate(updatedRestaurant)
            loadBlockedUsers(updatedRestaurant)
        }
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



