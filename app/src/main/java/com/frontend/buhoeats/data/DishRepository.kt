package com.frontend.buhoeats.data

import com.frontend.buhoeats.models.Dish

interface DishRepository {
    fun getDishesForRestaurant(restaurantId: String): List<Dish>
    fun addDishToRestaurant(restaurantId: String, dish: Dish)
    fun removeDish(restaurantId: String, dishId: String)
    fun updateDish(restaurantId: String, dish: Dish)
}

