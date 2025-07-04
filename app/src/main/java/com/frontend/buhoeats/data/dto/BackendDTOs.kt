package com.frontend.buhoeats.data.dto

import com.google.gson.annotations.SerializedName

// DTOs para recibir datos del backend (respuestas)
data class BackendUserDTO(
    @SerializedName("_id") val id: String,
    val name: String,
    val lastName: String,
    val email: String,
    val password: String? = null,
    val picture: String? = null,
    val rol: String,
    val favorites: List<String> = emptyList()
)

data class BackendRestaurantDTO(
    @SerializedName("_id") val id: String,
    val name: String,
    val description: String,
    val category: String,
    val email: String,
    val phone: String? = null,
    val direction: String,
    val image: String? = null,
    val idCoordinate: String? = null,
    val idPictures: String? = null,
    val idMenu: String? = null,
    val idPromotion: String? = null,
    val idUser: String,
    val idReviews: String? = null,
    val idBlocks: String? = null,
    val idTimeRange: String? = null,
    val onWait: Boolean? = null
)

data class BackendPlateDTO(
    @SerializedName("_id") val id: String,
    val name: String,
    val description: String,
    val category: String,
    val price: Double,
    val image: String? = null, // CORREGIDO: picture → image (para coincidir con backend)
    val idRestaurant: String? = null
)

data class BackendPromotionDTO(
    @SerializedName("_id") val id: String,
    val title: String,
    val description: String,
    val price: PriceDTO,
    val rules: String,
    val restaurantId: String,
    val image: String? = null
)

data class PriceDTO(
    val before: Double,
    val now: Double
)

data class BackendCoordinateDTO(
    @SerializedName("_id") val id: String,
    val lat: Double,
    val lng: Double
)

// DTOs para enviar datos al backend (requests)
data class CreateUserRequest(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val rol: String,
    val picture: String? = null
)

data class UpdateUserRequest(
    val name: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val picture: String? = null
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class CreateRestaurantRequest(
    val name: String,
    val description: String,
    val category: String,
    val email: String,
    val phone: String? = null,
    val direction: String,
    val image: String? = null
)

data class UpdateRestaurantRequest(
    val name: String? = null,
    val description: String? = null,
    val category: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val direction: String? = null,
    val image: String? = null
)

data class CreatePlateRequest(
    val name: String,
    val description: String,
    val category: String,
    val price: Double,
    val image: String? = null
)

data class UpdatePlateRequest(
    val name: String? = null,
    val description: String? = null,
    val category: String? = null,
    val price: Double? = null,
    val image: String? = null
)

data class CreatePromotionRequest(
    val title: String,
    val description: String,
    val price: PriceDTO,
    val rules: String,
    val restaurantId: String,
    val image: String? = null
)

data class UpdatePromotionRequest(
    val title: String? = null,
    val description: String? = null,
    val price: PriceDTO? = null,
    val rules: String? = null,
    val image: String? = null
)

data class AddFavoriteRequest(
    val idRestaurant: String
)

data class RemoveFavoriteRequest(
    val idRestaurant: String
)

data class ChangeRoleRequest(
    val rol: String
)

// NUEVOS DTOs FALTANTES PARA LOS ENDPOINTS ADICIONALES:

// MENU DTOs
data class BackendMenuDTO(
    @SerializedName("_id") val id: String,
    val restaurantId: String,
    val plates: List<String> // Array de IDs de platos
)

data class CreateMenuRequest(
    val restaurantId: String,
    val plates: List<String>
)

data class UpdateMenuRequest(
    val plates: List<String>? = null
)

// REVIEW DTOs
data class BackendReviewDTO(
    @SerializedName("_id") val id: String,
    val comment: String,
    val stars: Int,
    val idUser: String,
    val idrestaurant: String
)

data class CreateReviewRequest(
    val comment: String,
    val stars: Int,
    val idUser: String,
    val idrestaurant: String
)

// BLOCK DTOs
data class BackendBlockDTO(
    @SerializedName("_id") val id: String,
    val idUser: String,
    val idRestaurant: String
)

// TIME RANGE DTOs
data class BackendTimeRangeDTO(
    @SerializedName("_id") val id: String,
    val from: String, // Formato "09:00"
    val to: String    // Formato "22:00"
)

data class CreateTimeRangeRequest(
    val from: String,
    val to: String
)

data class CreateCoordinateRequest(
    val latitude: Double,
    val longitude: Double
)

// Respuestas genéricas del backend
data class BackendResponse<T>(
    val message: String,
    val data: T? = null
)

data class LoginResponse(
    val message: String,
    val token: String
)
