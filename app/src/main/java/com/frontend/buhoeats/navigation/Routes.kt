package com.frontend.buhoeats.navigation

sealed class Screen(val route: String) {
    object Menu : Screen("menu")
    object Restaurant : Screen("restaurante/{restaurantId}") {
        fun createRoute(restaurantId: Int) = "restaurante/$restaurantId"
    }
}