package com.frontend.buhoeats.data

import com.frontend.buhoeats.models.Restaurant

class InMemoryRestaurantRepository : RestaurantRepository {
    override fun getRestaurants(): List<Restaurant> {
        return InMemoryUserDataSource.getRestaurants()
    }

    override fun getRestaurantById(restaurantId: String): Restaurant? {
        return InMemoryUserDataSource.getRestaurantById(restaurantId)
    }

    override fun addRestaurant(restaurant: Restaurant) {
        InMemoryUserDataSource.addRestaurant(restaurant)
    }

    override fun updateRestaurant(updatedRestaurant: Restaurant) {
        InMemoryUserDataSource.updateRestaurant(updatedRestaurant)
    }

    override fun deleteRestaurant(restaurantId: String) {
        InMemoryUserDataSource.deleteRestaurant(restaurantId)
    }

    override fun getNextRestaurantId(): String {
        return InMemoryUserDataSource.getNextRestaurantId()
    }

    override fun getUserEmailById(userId: String): String {
        return InMemoryUserDataSource.getUserEmailById(userId)
    }
}

