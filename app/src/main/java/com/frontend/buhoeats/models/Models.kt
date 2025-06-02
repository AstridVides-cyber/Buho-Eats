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
    val categories: List<String>,
    val contactInfo: ContactInfo,
    val reviews: List<Review>,
    val menu: List<Dish>,
    val promos: List<Promo>,
    val latitud: Double,
    val longitud: Double

) : Serializable

data class ContactInfo(
    val email: String,
    val phone: String,
    val hours: String,
    val address: String
) : Serializable

data class Review(
    val username: String,
    val comment: String,
    val rating: Int
) : Serializable

data class Promo (
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: String,
    val promprice :String,
    val reglas : String
)