package com.frontend.buhoeats.data

import com.frontend.buhoeats.models.Restaurant

interface RestaurantRepository {
    fun getRestaurants(): List<Restaurant>
    fun getRestaurantById(restaurantId: String): Restaurant?
    fun addRestaurant(restaurant: Restaurant)
    fun updateRestaurant(updatedRestaurant: Restaurant)
    fun deleteRestaurant(restaurantId: String)
    fun getNextRestaurantId(): String
    fun getUserEmailById(userId: String): String
}

