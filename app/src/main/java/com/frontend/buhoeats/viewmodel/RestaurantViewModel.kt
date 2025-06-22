package com.frontend.buhoeats.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.frontend.buhoeats.data.InMemoryUserDataSource
import com.frontend.buhoeats.models.Promo
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.models.User

class RestaurantViewModel : ViewModel() {

    private val _restaurantList = mutableStateListOf<Restaurant>()
    val restaurantList: List<Restaurant> get() = _restaurantList

    init {
        loadRestaurants()
    }

    fun loadRestaurants() {
        _restaurantList.clear()
        _restaurantList.addAll(InMemoryUserDataSource.getRestaurants())
    }

    fun deleteRestaurant(restaurantId: Int) {
        InMemoryUserDataSource.deleteRestaurant(restaurantId)
        _restaurantList.removeIf { it.id == restaurantId }
    }

    fun addRestaurant(restaurant: Restaurant) {
        InMemoryUserDataSource.addRestaurant(restaurant)
        _restaurantList.add(restaurant)
    }

    fun updateRestaurant(updatedRestaurant: Restaurant) {
        InMemoryUserDataSource.updateRestaurant(updatedRestaurant)
        val index = _restaurantList.indexOfFirst { it.id == updatedRestaurant.id }
        if (index != -1) {
            _restaurantList[index] = updatedRestaurant
        }
    }

    fun getNextRestaurantId(): Int {
        return InMemoryUserDataSource.getNextRestaurantId()
    }

    fun getUserEmailById(userId: Int): String {
        return InMemoryUserDataSource.getUserEmailById(userId)
    }
}

class BlockedUsersViewModel : ViewModel() {
    private val _blockedUsers = mutableStateListOf<User>()
    val blockedUsers: List<User> get() = _blockedUsers

    fun loadBlockedUsers(restaurantId: Int) {
        val restaurant = InMemoryUserDataSource.getRestaurantById(restaurantId)
        _blockedUsers.clear()
        restaurant?.let {
            _blockedUsers.addAll(
                it.blockedUsers.mapNotNull { userId ->
                    InMemoryUserDataSource.getUserById(userId)
                }
            )
        }
    }

    fun unblockUser(user: User, restaurantId: Int, onUpdate: (Restaurant) -> Unit) {
        InMemoryUserDataSource.unblockUserFromRestaurant(user.id, restaurantId)
        _blockedUsers.remove(user)
        InMemoryUserDataSource.getRestaurantById(restaurantId)?.let {
            onUpdate(it)
            loadBlockedUsers(restaurantId)
        }
    }

    fun blockUser(user: User, restaurantId: Int, onUpdate: (Restaurant) -> Unit) {
        val restaurant = InMemoryUserDataSource.getRestaurantById(restaurantId)
        restaurant?.let {
            InMemoryUserDataSource.blockUserFromRestaurant(user.id, restaurantId)
            _blockedUsers.add(user)
            InMemoryUserDataSource.getRestaurantById(restaurantId)?.let { updatedRestaurant ->
                onUpdate(updatedRestaurant)
                loadBlockedUsers(restaurantId)
            }
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



