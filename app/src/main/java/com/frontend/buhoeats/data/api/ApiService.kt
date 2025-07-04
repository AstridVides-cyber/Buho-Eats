package com.frontend.buhoeats.data.api

import com.frontend.buhoeats.data.dto.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // USER ENDPOINTS - CORREGIDO: Agregado prefijo /api/usuario
    @POST("api/usuario/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("api/usuario/create")
    suspend fun createUser(@Body request: CreateUserRequest): Response<BackendResponse<BackendUserDTO>>

    @GET("api/usuario/all")
    suspend fun getAllUsers(): Response<BackendResponse<List<BackendUserDTO>>>

    @GET("api/usuario/{id}")
    suspend fun getUserById(
        @Path("id") id: String,
        @Body request: Map<String, String> // Para el email que requiere el endpoint
    ): Response<BackendResponse<BackendUserDTO>>

    @PUT("api/usuario/{id}")
    suspend fun updateUser(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body request: UpdateUserRequest
    ): Response<BackendResponse<String>>

    @DELETE("api/usuario/{id}")
    suspend fun deleteUser(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<BackendResponse<String>>

    @PUT("api/usuario/{id}/rol")
    suspend fun changeUserRole(
        @Path("id") id: String,
        @Body request: ChangeRoleRequest
    ): Response<BackendResponse<BackendUserDTO>>

    // FAVORITES ENDPOINTS - CORREGIDO: Rutas actualizadas
    @POST("api/usuario/{id}/favoritos/add")
    suspend fun addFavorite(
        @Path("id") userId: String,
        @Header("Authorization") token: String,
        @Body request: AddFavoriteRequest
    ): Response<BackendResponse<BackendUserDTO>>

    @DELETE("api/usuario/{id}/favoritos/{restaurantId}")
    suspend fun removeFavorite(
        @Path("id") userId: String,
        @Path("restaurantId") restaurantId: String,
        @Header("Authorization") token: String
    ): Response<BackendResponse<BackendUserDTO>>

    // RESTAURANT ENDPOINTS - CORREGIDO: Agregado prefijo /api/restaurante
    @POST("api/restaurante/create")
    suspend fun createRestaurant(
        @Header("Authorization") token: String,
        @Body request: CreateRestaurantRequest
    ): Response<BackendResponse<BackendRestaurantDTO>>

    @GET("api/restaurante/all")
    suspend fun getAllRestaurants(): Response<BackendResponse<List<BackendRestaurantDTO>>>

    @GET("api/restaurante/{id}")
    suspend fun getRestaurantById(@Path("id") id: String): Response<BackendResponse<BackendRestaurantDTO>>

    @PUT("api/restaurante/{id}")
    suspend fun updateRestaurant(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Body request: UpdateRestaurantRequest
    ): Response<BackendResponse<BackendRestaurantDTO>>

    @DELETE("api/restaurante/{id}")
    suspend fun deleteRestaurant(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<BackendResponse<String>>

    // PLATE ENDPOINTS - CORREGIDO: Rutas anidadas bajo restaurante/menu
    @POST("api/restaurante/{restaurantId}/menu/plato/create")
    suspend fun createPlate(
        @Path("restaurantId") restaurantId: String,
        @Header("Authorization") token: String,
        @Body request: CreatePlateRequest
    ): Response<BackendResponse<BackendPlateDTO>>

    @GET("api/restaurante/{restaurantId}/menu/plato/all")
    suspend fun getAllPlates(
        @Path("restaurantId") restaurantId: String
    ): Response<BackendResponse<List<BackendPlateDTO>>>

    @GET("api/restaurante/{restaurantId}/menu/plato/{id}")
    suspend fun getPlateById(
        @Path("restaurantId") restaurantId: String,
        @Path("id") plateId: String
    ): Response<BackendResponse<BackendPlateDTO>>

    @PUT("api/restaurante/{restaurantId}/menu/plato/{id}")
    suspend fun updatePlate(
        @Path("restaurantId") restaurantId: String,
        @Path("id") plateId: String,
        @Header("Authorization") token: String,
        @Body request: UpdatePlateRequest
    ): Response<BackendResponse<String>>

    @DELETE("api/restaurante/{restaurantId}/menu/plato/{id}")
    suspend fun deletePlate(
        @Path("restaurantId") restaurantId: String,
        @Path("id") plateId: String,
        @Header("Authorization") token: String
    ): Response<BackendResponse<String>>

    // PROMOTION ENDPOINTS - CORREGIDO: Rutas anidadas bajo restaurante
    @POST("api/restaurante/{restaurantId}/promotion/create")
    suspend fun createPromotion(
        @Path("restaurantId") restaurantId: String,
        @Header("Authorization") token: String,
        @Body request: CreatePromotionRequest
    ): Response<BackendResponse<BackendPromotionDTO>>

    @GET("api/restaurante/{restaurantId}/promotion/{promotionId}")
    suspend fun getPromotionById(
        @Path("restaurantId") restaurantId: String,
        @Path("promotionId") promotionId: String
    ): Response<BackendResponse<BackendPromotionDTO>>

    @PUT("api/restaurante/{restaurantId}/promotion/{promotionId}")
    suspend fun updatePromotion(
        @Path("restaurantId") restaurantId: String,
        @Path("promotionId") promotionId: String,
        @Header("Authorization") token: String,
        @Body request: UpdatePromotionRequest
    ): Response<BackendResponse<BackendPromotionDTO>>

    @DELETE("api/restaurante/{restaurantId}/promotion/{promotionId}")
    suspend fun deletePromotion(
        @Path("restaurantId") restaurantId: String,
        @Path("promotionId") promotionId: String,
        @Header("Authorization") token: String
    ): Response<BackendResponse<String>>

    // NUEVOS ENDPOINTS QUE FALTABAN SEGÚN EL BACKEND:

    // MENU ENDPOINTS
    @POST("api/restaurante/{restaurantId}/menu/create")
    suspend fun createMenu(
        @Path("restaurantId") restaurantId: String,
        @Header("Authorization") token: String,
        @Body request: CreateMenuRequest
    ): Response<BackendResponse<BackendMenuDTO>>

    @GET("api/restaurante/{restaurantId}/menu/all")
    suspend fun getAllMenus(
        @Path("restaurantId") restaurantId: String
    ): Response<BackendResponse<List<BackendMenuDTO>>>

    @GET("api/restaurante/{restaurantId}/menu/{menuId}")
    suspend fun getMenuById(
        @Path("restaurantId") restaurantId: String,
        @Path("menuId") menuId: String
    ): Response<BackendResponse<BackendMenuDTO>>

    @PUT("api/restaurante/{restaurantId}/menu/{menuId}")
    suspend fun updateMenu(
        @Path("restaurantId") restaurantId: String,
        @Path("menuId") menuId: String,
        @Header("Authorization") token: String,
        @Body request: UpdateMenuRequest
    ): Response<BackendResponse<BackendMenuDTO>>

    @DELETE("api/restaurante/{restaurantId}/menu/{menuId}")
    suspend fun deleteMenu(
        @Path("restaurantId") restaurantId: String,
        @Path("menuId") menuId: String,
        @Header("Authorization") token: String
    ): Response<BackendResponse<String>>

    // REVIEW ENDPOINTS
    @POST("api/restaurante/{restaurantId}/review/create")
    suspend fun createReview(
        @Path("restaurantId") restaurantId: String,
        @Header("Authorization") token: String,
        @Body request: CreateReviewRequest
    ): Response<BackendResponse<BackendReviewDTO>>

    @GET("api/restaurante/{restaurantId}/review/all")
    suspend fun getAllReviews(
        @Path("restaurantId") restaurantId: String,
        @Header("Authorization") token: String
    ): Response<BackendResponse<List<BackendReviewDTO>>>

    @GET("api/restaurante/{restaurantId}/review/{id}")
    suspend fun getReviewById(
        @Path("restaurantId") restaurantId: String,
        @Path("id") reviewId: String,
        @Header("Authorization") token: String
    ): Response<BackendResponse<BackendReviewDTO>>

    @DELETE("api/restaurante/{restaurantId}/review/{id}")
    suspend fun deleteReview(
        @Path("restaurantId") restaurantId: String,
        @Path("id") reviewId: String,
        @Header("Authorization") token: String
    ): Response<BackendResponse<String>>

    // BLOCK ENDPOINTS
    @POST("api/restaurante/{restaurantId}/bloquear/{id}/bloquear/{usuarioId}")
    suspend fun blockUser(
        @Path("restaurantId") restaurantId: String,
        @Path("id") id: String,
        @Path("usuarioId") usuarioId: String,
        @Header("Authorization") token: String
    ): Response<BackendResponse<BackendBlockDTO>>

    @DELETE("api/restaurante/{restaurantId}/bloquear/{restauranteId}/desbloquear/{usuarioId}")
    suspend fun unblockUser(
        @Path("restaurantId") restaurantId: String,
        @Path("restauranteId") restauranteId: String,
        @Path("usuarioId") usuarioId: String,
        @Header("Authorization") token: String
    ): Response<BackendResponse<String>>

    // TIME RANGE ENDPOINTS
    @POST("api/timeRange/create")
    suspend fun createTimeRange(
        @Header("Authorization") token: String,
        @Body request: CreateTimeRangeRequest
    ): Response<BackendResponse<BackendTimeRangeDTO>>

    @GET("api/timeRange/{id}")
    suspend fun getTimeRangeById(@Path("id") id: String): Response<BackendResponse<BackendTimeRangeDTO>>

    // COORDINATE ENDPOINTS
    @POST("api/coordenadas/create")
    suspend fun createCoordinate(
        @Header("Authorization") token: String,
        @Body request: CreateCoordinateRequest
    ): Response<BackendResponse<BackendCoordinateDTO>>

    @GET("api/coordenadas/all")
    suspend fun getAllCoordinates(): Response<BackendResponse<List<BackendCoordinateDTO>>>
}
