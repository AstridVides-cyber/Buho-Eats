package com.frontend.buhoeats.data

import com.frontend.buhoeats.models.User

object DummyData {
    private var user = User(
        id = 1,
        name = "Astrid",
        lastName = "Vides",
        imageProfile = "",
        email = "vides67@gmail.com",
        password = "astrid",
        confirmpassword = "astrid"
    )

    fun getUser(): User {
        return user
    }

    fun updateUser(newUser: User) {
        user = newUser
    }




}
