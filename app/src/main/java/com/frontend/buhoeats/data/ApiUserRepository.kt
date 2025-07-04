package com.frontend.buhoeats.data

import com.frontend.buhoeats.data.api.ApiClient
import com.frontend.buhoeats.data.mappers.DataMappers.toUser
import com.frontend.buhoeats.data.mappers.DataMappers.toCreateUserRequest
import com.frontend.buhoeats.data.mappers.DataMappers.toUpdateUserRequest
import com.frontend.buhoeats.data.mappers.DataMappers.createLoginRequest
import com.frontend.buhoeats.data.mappers.DataMappers.createAddFavoriteRequest
import com.frontend.buhoeats.data.mappers.DataMappers.createRemoveFavoriteRequest
import com.frontend.buhoeats.data.mappers.DataMappers.createChangeRoleRequest
import com.frontend.buhoeats.models.User
import kotlinx.coroutines.runBlocking

class ApiUserRepository : UserRepository {
    private val apiService = ApiClient.apiService
    private var currentToken: String? = null

    fun setAuthToken(token: String) {
        currentToken = token
    }

    private fun getAuthHeader(): String {
        return "Bearer ${currentToken ?: ""}"
    }

    override fun getUsers(): List<User> {
        return runBlocking {
            try {
                val response = apiService.getAllUsers()
                if (response.isSuccessful) {
                    response.body()?.data?.map { it.toUser() } ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    override fun getUserByEmail(email: String): User? {
        return runBlocking {
            try {
                // BÚSQUEDA MANUAL: Obtener todos los usuarios y filtrar por email
                val allUsers = getUsers()
                allUsers.find { it.email.equals(email, ignoreCase = true) }
            } catch (e: Exception) {
                null
            }
        }
    }

    override fun registerUser(newUser: User): Boolean {
        return runBlocking {
            try {
                // VALIDACIÓN MANUAL: Verificar si el email ya existe
                val existingUser = getUserByEmail(newUser.email)
                if (existingUser != null) {
                    return@runBlocking false // Email ya existe
                }

                val request = newUser.toCreateUserRequest()
                val response = apiService.createUser(request)
                response.isSuccessful
            } catch (e: Exception) {
                false
            }
        }
    }

    override fun updateUser(user: User): Boolean {
        return runBlocking {
            try {
                val request = user.toUpdateUserRequest()
                val response = apiService.updateUser(user.id, getAuthHeader(), request)
                response.isSuccessful
            } catch (e: Exception) {
                false
            }
        }
    }

    override fun assignRoleToUser(email: String, newRole: String): Boolean {
        return runBlocking {
            try {
                // BÚSQUEDA MANUAL: Encontrar usuario por email
                val user = getUserByEmail(email)
                if (user != null) {
                    val request = createChangeRoleRequest(newRole)
                    val response = apiService.changeUserRole(user.id, request)
                    response.isSuccessful
                } else {
                    false
                }
            } catch (e: Exception) {
                false
            }
        }
    }

    override fun getUserById(userId: String): User? {
        return runBlocking {
            try {
                // BÚSQUEDA MANUAL: Obtener todos los usuarios y filtrar por ID
                val allUsers = getUsers()
                allUsers.find { it.id == userId }
            } catch (e: Exception) {
                null
            }
        }
    }

    override fun blockUserFromRestaurant(userId: String, restaurantId: String) {
        // IMPLEMENTACIÓN MANUAL: Como no hay endpoint, podemos manejarlo localmente
        // o implementar lógica del lado del cliente
    }

    override fun unblockUserFromRestaurant(userId: String, restaurantId: String) {
        // IMPLEMENTACIÓN MANUAL: Como no hay endpoint, podemos manejarlo localmente
        // o implementar lógica del lado del cliente
    }

    // Funciones adicionales para manejar el login y favoritos
    fun login(email: String, password: String): String? {
        return runBlocking {
            try {
                val request = createLoginRequest(email, password)
                val response = apiService.login(request)
                if (response.isSuccessful) {
                    val token = response.body()?.token
                    if (token != null) {
                        setAuthToken(token)
                    }
                    token
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    fun addFavorite(userId: String, restaurantId: String): Boolean {
        return runBlocking {
            try {
                val request = createAddFavoriteRequest(restaurantId)
                val response = apiService.addFavorite(userId, getAuthHeader(), request)
                response.isSuccessful
            } catch (e: Exception) {
                false
            }
        }
    }

    fun removeFavorite(userId: String, restaurantId: String): Boolean {
        return runBlocking {
            try {
                // CORREGIDO: El endpoint DELETE no necesita body, usa path parameters
                val response = apiService.removeFavorite(userId, restaurantId, getAuthHeader())
                response.isSuccessful
            } catch (e: Exception) {
                false
            }
        }
    }
}
