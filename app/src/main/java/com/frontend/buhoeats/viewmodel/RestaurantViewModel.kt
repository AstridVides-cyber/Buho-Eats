package com.frontend.buhoeats.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.frontend.buhoeats.data.InMemoryUserDataSource
import com.frontend.buhoeats.models.Promo
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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

    fun deleteRestaurant(restaurantId: String) {
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

    fun getNextRestaurantId(): String {
        return InMemoryUserDataSource.getNextRestaurantId()
    }

    fun getUserEmailById(userId: String): String {
        return InMemoryUserDataSource.getUserEmailById(userId)
    }
    fun updateRestaurantImage(restaurantId: String, newImageUrl: String) {
        val restaurant = InMemoryUserDataSource.getRestaurantById(restaurantId)
        restaurant?.let {
            val updatedRestaurant = it.copy(imageUrl = newImageUrl)
            InMemoryUserDataSource.updateRestaurant(updatedRestaurant)
            val index = _restaurantList.indexOfFirst { r -> r.id == restaurantId }
            if (index != -1) {
                _restaurantList[index] = updatedRestaurant
            }
        }
    }
    fun removeDishFromRestaurant(restaurantId: String, dishId: String) {
        val restaurant = InMemoryUserDataSource.getRestaurantById(restaurantId)
        restaurant?.let {
            val updatedMenu = it.menu.filterNot { dish -> dish.id == dishId }
            val updatedRestaurant = it.copy(menu = updatedMenu)
            InMemoryUserDataSource.updateRestaurant(updatedRestaurant)
            val index = _restaurantList.indexOfFirst { r -> r.id == restaurantId }
            if (index != -1) {
                _restaurantList[index] = updatedRestaurant
            }
        }
    }
}

class BlockedUsersViewModel : ViewModel() {
    private val _blockedUsers = mutableStateListOf<User>()
    val blockedUsers: List<User> get() = _blockedUsers

    fun loadBlockedUsers(restaurantId: String) {
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

    fun unblockUser(user: User, restaurantId: String, onUpdate: (Restaurant) -> Unit) {
        InMemoryUserDataSource.unblockUserFromRestaurant(user.id, restaurantId)
        _blockedUsers.remove(user)
        InMemoryUserDataSource.getRestaurantById(restaurantId)?.let {
            onUpdate(it)
            loadBlockedUsers(restaurantId)
        }
    }

    fun blockUser(user: User, restaurantId: String, onUpdate: (Restaurant) -> Unit) {
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
    private val _promos = MutableStateFlow<List<Promo>>(emptyList())
    val promos: StateFlow<List<Promo>> = _promos

    fun loadPromosForUser(currentUser: User?) {
        val promosToDisplay = when (currentUser?.rol) {
            "admin" -> {
                val restaurant = InMemoryUserDataSource.getRestaurants()
                    .firstOrNull { it.admin == currentUser.id }
                restaurant?.promos ?: emptyList()
            }
            else -> InMemoryUserDataSource.getRestaurants().flatMap { it.promos }
        }
        _promos.value = promosToDisplay
    }


    fun deletePromo(promo: Promo, user: User?) {
        InMemoryUserDataSource.removePromo(promo)
        loadPromosForUser(user)
    }

    fun updatePromo(updatedPromo: Promo, user: User?) {
        InMemoryUserDataSource.updatePromo(updatedPromo)
        loadPromosForUser(user)
    }

    fun addPromo(promo: Promo, user: User?) {
        InMemoryUserDataSource.addPromoToRestaurant(promo.restaurantId, promo)
        loadPromosForUser(user)
    }
}
