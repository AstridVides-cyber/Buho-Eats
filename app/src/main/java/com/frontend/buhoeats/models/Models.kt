package com.frontend.buhoeats.models

import java.io.Serializable

data class User(
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val confirmpassword: String,
    val imageProfile: String,

) : Serializable