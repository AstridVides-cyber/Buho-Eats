package com.frontend.buhoeats.models

import java.io.Serializable

data class User(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val imageProfile: String,
    val rol: String,
    var favoritos: MutableList<Int> = mutableListOf()
)
: Serializable

data class Dish(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: String
) : Serializable

data class Restaurant(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val category: String,
    val contactInfo: ContactInfo,
    val ratings: MutableList<Rating>,
    val comments: MutableList<Comment>,
    val menu: List<Dish>,
    var promos: List<Promo>,
    val latitud: Double,
    val longitud: Double,
    val admin : Int,
    var blockedUsers: List<Int> = emptyList()
) : Serializable

data class ContactInfo(
    val email: String,
    val phone: String,
    val hours: String,
    val address: String
) : Serializable

data class Rating(
    val userId: Int,
    val rating: Int
) : Serializable

data class Comment(
    val userId: Int,
    val comment: String
) : Serializable

data class Promo (
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: String,
    val promprice :String,
    val reglas : String,
    val restaurantId: Int
)