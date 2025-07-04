package com.frontend.buhoeats.data.config

import com.frontend.buhoeats.data.*

object RepositoryProvider {

    // Configuración: cambia esto a true para usar API, false para usar InMemory
    private const val USE_API = false // Cambiar a true cuando quieras usar el backend

    // Instancias singleton de los repositorios
    private val inMemoryUserRepository by lazy { InMemoryUserRepository() }
    private val apiUserRepository by lazy { ApiUserRepository() }

    private val inMemoryRestaurantRepository by lazy { InMemoryRestaurantRepository() }
    private val apiRestaurantRepository by lazy { ApiRestaurantRepository() }

    private val inMemoryDishRepository by lazy { InMemoryDishRepository() }
    private val apiDishRepository by lazy { ApiDishRepository() }

    private val inMemoryPromoRepository by lazy { InMemoryPromoRepository() }
    private val apiPromoRepository by lazy { ApiPromoRepository() }

    // Funciones públicas para obtener los repositorios
    fun getUserRepository(): UserRepository {
        return if (USE_API) apiUserRepository else inMemoryUserRepository
    }

    fun getRestaurantRepository(): RestaurantRepository {
        return if (USE_API) apiRestaurantRepository else inMemoryRestaurantRepository
    }

    fun getDishRepository(): DishRepository {
        return if (USE_API) apiDishRepository else inMemoryDishRepository
    }

    fun getPromoRepository(): PromoRepository {
        return if (USE_API) apiPromoRepository else inMemoryPromoRepository
    }

    // Funciones para configurar el token cuando uses API
    fun setAuthToken(token: String) {
        if (USE_API) {
            (apiUserRepository as? ApiUserRepository)?.setAuthToken(token)
            (apiRestaurantRepository as? ApiRestaurantRepository)?.setAuthToken(token)
            (apiDishRepository as? ApiDishRepository)?.setAuthToken(token)
            (apiPromoRepository as? ApiPromoRepository)?.setAuthToken(token)
        }
    }

    // Función para hacer login cuando uses API
    fun login(email: String, password: String): String? {
        return if (USE_API) {
            apiUserRepository.login(email, password)
        } else {
            // Para InMemory, simplemente devolvemos un token ficticio si el usuario existe
            val user = inMemoryUserRepository.getUserByEmail(email)
            if (user != null && user.password == password) {
                "fake_token_for_inmemory"
            } else {
                null
            }
        }
    }

    // Funciones adicionales para API
    fun addFavorite(userId: String, restaurantId: String): Boolean {
        return if (USE_API) {
            apiUserRepository.addFavorite(userId, restaurantId)
        } else {
            // Implementar lógica para InMemory si es necesario
            false
        }
    }

    fun removeFavorite(userId: String, restaurantId: String): Boolean {
        return if (USE_API) {
            apiUserRepository.removeFavorite(userId, restaurantId)
        } else {
            // Implementar lógica para InMemory si es necesario
            false
        }
    }

    // Función para verificar si estás usando API
    fun isUsingApi(): Boolean = USE_API
}
