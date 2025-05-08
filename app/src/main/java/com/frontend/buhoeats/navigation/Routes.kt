package com.frontend.buhoeats.navigation


sealed class Screens(val route: String) {
    object Login : Screens("login")
}