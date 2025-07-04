package com.frontend.buhoeats.data

import com.frontend.buhoeats.data.api.ApiClient
import com.frontend.buhoeats.data.mappers.DataMappers.toDish
import com.frontend.buhoeats.data.mappers.DataMappers.toCreatePlateRequest
import com.frontend.buhoeats.data.mappers.DataMappers.toUpdatePlateRequest
import com.frontend.buhoeats.models.Dish
import kotlinx.coroutines.runBlocking

class ApiDishRepository : DishRepository {
    private val apiService = ApiClient.apiService
    private var currentToken: String? = null

    fun setAuthToken(token: String) {
        currentToken = token
    }

    private fun getAuthHeader(): String {
        return "Bearer ${currentToken ?: ""}"
    }

    override fun getDishesForRestaurant(restaurantId: String): List<Dish> {
        return runBlocking {
            try {
                // BÚSQUEDA MANUAL: Obtener todos los platos y filtrar por restaurante
                val response = apiService.getAllPlates()
                if (response.isSuccessful) {
                    response.body()?.data
                        ?.filter { it.idRestaurant == restaurantId }
                        ?.map { it.toDish() } ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    override fun addDishToRestaurant(restaurantId: String, dish: Dish) {
        runBlocking {
            try {
                val request = dish.toCreatePlateRequest()
                apiService.createPlate(getAuthHeader(), request)
            } catch (e: Exception) {
                // Log error or handle appropriately
            }
        }
    }

    override fun removeDish(restaurantId: String, dishId: String) {
        runBlocking {
            try {
                apiService.deletePlate(dishId, getAuthHeader())
            } catch (e: Exception) {
                // Log error or handle appropriately
            }
        }
    }

    override fun updateDish(restaurantId: String, dish: Dish) {
        runBlocking {
            try {
                val request = dish.toUpdatePlateRequest()
                apiService.updatePlate(dish.id, getAuthHeader(), request)
            } catch (e: Exception) {
                // Log error or handle appropriately
            }
        }
    }

    // Funciones adicionales con búsqueda manual
    fun getAllDishes(): List<Dish> {
        return runBlocking {
            try {
                val response = apiService.getAllPlates()
                if (response.isSuccessful) {
                    response.body()?.data?.map { it.toDish() } ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    fun getDishById(dishId: String): Dish? {
        return runBlocking {
            try {
                // BÚSQUEDA MANUAL: Obtener todos los platos y filtrar por ID
                val allDishes = getAllDishes()
                allDishes.find { it.id == dishId }
            } catch (e: Exception) {
                null
            }
        }
    }

    fun getDishesByCategory(category: String): List<Dish> {
        return runBlocking {
            try {
                // BÚSQUEDA MANUAL: Obtener todos los platos y filtrar por categoría
                val allDishes = getAllDishes()
                allDishes.filter { dish ->
                    // Nota: tu modelo Dish no tiene category, pero podrías agregarlo si lo necesitas
                    true // Por ahora devuelve todos
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
}
