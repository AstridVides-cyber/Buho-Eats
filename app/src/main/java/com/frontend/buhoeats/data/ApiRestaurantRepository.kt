package com.frontend.buhoeats.data

import com.frontend.buhoeats.data.api.ApiClient
import com.frontend.buhoeats.data.mappers.DataMappers.toRestaurant
import com.frontend.buhoeats.data.mappers.DataMappers.toCreateRestaurantRequest
import com.frontend.buhoeats.data.mappers.DataMappers.toUpdateRestaurantRequest
import com.frontend.buhoeats.models.Restaurant
import kotlinx.coroutines.runBlocking
import java.util.UUID

class ApiRestaurantRepository : RestaurantRepository {
    private val apiService = ApiClient.apiService
    private var currentToken: String? = null

    fun setAuthToken(token: String) {
        currentToken = token
    }

    private fun getAuthHeader(): String {
        return "Bearer ${currentToken ?: ""}"
    }

    override fun getRestaurants(): List<Restaurant> {
        return runBlocking {
            try {
                val response = apiService.getAllRestaurants()
                if (response.isSuccessful) {
                    response.body()?.data?.map { it.toRestaurant() } ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    override fun getRestaurantById(restaurantId: String): Restaurant? {
        return runBlocking {
            try {
                val response = apiService.getRestaurantById(restaurantId)
                if (response.isSuccessful) {
                    response.body()?.data?.toRestaurant()
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    override fun addRestaurant(restaurant: Restaurant) {
        runBlocking {
            try {
                val request = restaurant.toCreateRestaurantRequest()
                apiService.createRestaurant(getAuthHeader(), request)
            } catch (e: Exception) {
                // Log error or handle appropriately
            }
        }
    }

    override fun updateRestaurant(updatedRestaurant: Restaurant) {
        runBlocking {
            try {
                val request = updatedRestaurant.toUpdateRestaurantRequest()
                apiService.updateRestaurant(updatedRestaurant.id, getAuthHeader(), request)
            } catch (e: Exception) {
                // Log error or handle appropriately
            }
        }
    }

    override fun deleteRestaurant(restaurantId: String) {
        runBlocking {
            try {
                apiService.deleteRestaurant(restaurantId, getAuthHeader())
            } catch (e: Exception) {
                // Log error or handle appropriately
            }
        }
    }

    override fun getNextRestaurantId(): String {
        // Para la API, los IDs los genera el backend, así que retornamos uno temporal
        return UUID.randomUUID().toString()
    }

    override fun getUserEmailById(userId: String): String {
        return runBlocking {
            try {
                // Usamos el UserRepository para obtener el email del usuario
                val emailBody = mapOf("email" to "")
                val response = apiService.getUserById(userId, emailBody)
                if (response.isSuccessful) {
                    response.body()?.data?.email ?: ""
                } else {
                    ""
                }
            } catch (e: Exception) {
                ""
            }
        }
    }
}
