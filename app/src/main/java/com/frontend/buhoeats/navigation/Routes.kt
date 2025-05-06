package com.frontend.buhoeats.navigation

sealed class Screens(val route: String) {
    object Profile : Screens("profile")
    object Settings : Screens("settings")
}
