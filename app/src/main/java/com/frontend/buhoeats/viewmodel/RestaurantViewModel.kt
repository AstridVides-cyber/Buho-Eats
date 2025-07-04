package com.frontend.buhoeats.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.frontend.buhoeats.data.DishRepository
import com.frontend.buhoeats.data.InMemoryDishRepository
import com.frontend.buhoeats.data.PromoRepository
import com.frontend.buhoeats.data.InMemoryPromoRepository
import com.frontend.buhoeats.data.RestaurantRepository
import com.frontend.buhoeats.data.InMemoryRestaurantRepository
import com.frontend.buhoeats.data.UserRepository
import com.frontend.buhoeats.data.InMemoryUserRepository
import com.frontend.buhoeats.models.Promo
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RestaurantViewModel(
    private val dishRepository: DishRepository = InMemoryDishRepository(),
    private val restaurantRepository: RestaurantRepository = InMemoryRestaurantRepository()
) : ViewModel() {
    private val _restaurantList = mutableStateListOf<Restaurant>()
    val restaurantList: List<Restaurant> get() = _restaurantList

    init {
        loadRestaurants()
    }

    fun loadRestaurants() {
        _restaurantList.clear()
        _restaurantList.addAll(restaurantRepository.getRestaurants())
    }

    fun deleteRestaurant(restaurantId: String) {
        restaurantRepository.deleteRestaurant(restaurantId)
        _restaurantList.removeIf { it.id == restaurantId }
    }

    fun addRestaurant(restaurant: Restaurant) {
        restaurantRepository.addRestaurant(restaurant)
        _restaurantList.add(restaurant)
    }

    fun updateRestaurant(updatedRestaurant: Restaurant) {
        restaurantRepository.updateRestaurant(updatedRestaurant)
        val index = _restaurantList.indexOfFirst { it.id == updatedRestaurant.id }
        if (index != -1) {
            _restaurantList[index] = updatedRestaurant
        }
    }

    fun getNextRestaurantId(): String {
        return restaurantRepository.getNextRestaurantId()
    }

    fun getUserEmailById(userId: String): String {
        return restaurantRepository.getUserEmailById(userId)
    }
    fun updateRestaurantImage(restaurantId: String, newImageUrl: String) {
        val restaurant = restaurantRepository.getRestaurantById(restaurantId)
        restaurant?.let {
            val updatedRestaurant = it.copy(imageUrl = newImageUrl)
            restaurantRepository.updateRestaurant(updatedRestaurant)
            val index = _restaurantList.indexOfFirst { r -> r.id == restaurantId }
            if (index != -1) {
                _restaurantList[index] = updatedRestaurant
            }
        }
    }
    fun removeDishFromRestaurant(restaurantId: String, dishId: String) {
        dishRepository.removeDish(restaurantId, dishId)
        val restaurant = restaurantRepository.getRestaurantById(restaurantId)
        restaurant?.let {
            val index = _restaurantList.indexOfFirst { r -> r.id == restaurantId }
            if (index != -1) {
                _restaurantList[index] = it
            }
        }
    }
}

class BlockedUsersViewModel(
    private val userRepository: UserRepository = InMemoryUserRepository(),
    private val restaurantRepository: RestaurantRepository = InMemoryRestaurantRepository()
) : ViewModel() {
    private val _blockedUsers = mutableStateListOf<User>()
    val blockedUsers: List<User> get() = _blockedUsers

    fun loadBlockedUsers(restaurantId: String) {
        val restaurant = restaurantRepository.getRestaurantById(restaurantId)
        _blockedUsers.clear()
        restaurant?.let {
            _blockedUsers.addAll(
                it.blockedUsers.mapNotNull { userId ->
                    userRepository.getUserById(userId)
                }
            )
        }
    }

    fun unblockUser(user: User, restaurantId: String, onUpdate: (Restaurant) -> Unit) {
        userRepository.unblockUserFromRestaurant(user.id, restaurantId)
        _blockedUsers.remove(user)
        restaurantRepository.getRestaurantById(restaurantId)?.let {
            onUpdate(it)
            loadBlockedUsers(restaurantId)
        }
    }

    fun blockUser(user: User, restaurantId: String, onUpdate: (Restaurant) -> Unit) {
        val restaurant = restaurantRepository.getRestaurantById(restaurantId)
        restaurant?.let {
            userRepository.blockUserFromRestaurant(user.id, restaurantId)
            _blockedUsers.add(user)
            restaurantRepository.getRestaurantById(restaurantId)?.let { updatedRestaurant ->
                onUpdate(updatedRestaurant)
                loadBlockedUsers(restaurantId)
            }
        }
    }
}

class PromoViewModel(
    private val promoRepository: PromoRepository = InMemoryPromoRepository(),
    private val userRepository: UserRepository = InMemoryUserRepository(),
    private val restaurantRepository: RestaurantRepository = InMemoryRestaurantRepository()
) : ViewModel() {
    private val _promos = MutableStateFlow<List<Promo>>(emptyList())
    val promos: StateFlow<List<Promo>> = _promos

    fun loadPromosForUser(currentUser: User?) {
        val promosToDisplay = when (currentUser?.rol) {
            "admin" -> {
                val restaurantId = restaurantRepository.getRestaurants()
                    .firstOrNull { it.admin == currentUser.id }?.id
                restaurantId?.let { promoRepository.getPromosForRestaurant(it) } ?: emptyList()
            }
            else -> promoRepository.getAllPromos()
        }
        _promos.value = promosToDisplay
    }

    fun deletePromo(promo: Promo, user: User?) {
        promoRepository.removePromo(promo)
        loadPromosForUser(user)
    }

    fun updatePromo(updatedPromo: Promo, user: User?) {
        promoRepository.updatePromo(updatedPromo)
        loadPromosForUser(user)
    }

    fun addPromo(promo: Promo, user: User?) {
        promoRepository.addPromoToRestaurant(promo.restaurantId, promo)
        loadPromosForUser(user)
    }
}
