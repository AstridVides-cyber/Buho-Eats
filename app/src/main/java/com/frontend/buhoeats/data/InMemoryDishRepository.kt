package com.frontend.buhoeats.data

import com.frontend.buhoeats.models.Dish

class InMemoryDishRepository : DishRepository {
    override fun getDishesForRestaurant(restaurantId: String): List<Dish> {
        return InMemoryUserDataSource.getRestaurantById(restaurantId)?.menu ?: emptyList()
    }

    override fun addDishToRestaurant(restaurantId: String, dish: Dish) {
        val restaurant = InMemoryUserDataSource.getRestaurantById(restaurantId)
        restaurant?.let {
            val updatedMenu = it.menu.toMutableList().apply { add(dish) }
            val updatedRestaurant = it.copy(menu = updatedMenu)
            InMemoryUserDataSource.updateRestaurant(updatedRestaurant)
        }
    }

    override fun removeDish(restaurantId: String, dishId: String) {
        val restaurant = InMemoryUserDataSource.getRestaurantById(restaurantId)
        restaurant?.let {
            val updatedMenu = it.menu.filterNot { dish -> dish.id == dishId }
            val updatedRestaurant = it.copy(menu = updatedMenu)
            InMemoryUserDataSource.updateRestaurant(updatedRestaurant)
        }
    }

    override fun updateDish(restaurantId: String, dish: Dish) {
        val restaurant = InMemoryUserDataSource.getRestaurantById(restaurantId)
        restaurant?.let {
            val updatedMenu = it.menu.map { d -> if (d.id == dish.id) dish else d }
            val updatedRestaurant = it.copy(menu = updatedMenu)
            InMemoryUserDataSource.updateRestaurant(updatedRestaurant)
        }
    }
}

