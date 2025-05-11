package com.frontend.buhoeats.data

import com.frontend.buhoeats.models.User

object DummyData {

    fun getUser(): List<User> {
        return listOf(
            User(
                id = 1,
                name = "Astrid",
                lastName = "Vides",
                imageProfile = "",
                email = "vides67@gmail.com"

            )
            )
    }
}
