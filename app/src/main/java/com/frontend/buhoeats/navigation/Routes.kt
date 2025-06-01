package com.frontend.buhoeats.navigation


sealed class Screens(val route: String) {
    object Profile : Screens("profile")
    object Settings : Screens("settings")
    object MyAccount : Screens ("myaccount")
    object Login : Screens("login")
    object SignUp : Screens("signup")
    object Home: Screens("home")
    object Restaurant : Screens("restaurante/{restaurantId}") {
        fun createRoute(restaurantId: Int) = "restaurante/$restaurantId"
    }
    object Favorites : Screens("favorites")
    object Search : Screens("search")
    object Map : Screens("map")
    object Promocion : Screens("promocion")
    object PromoInfo : Screens("promoInfo/{promoId}") {
        fun createRoute(promoId: Int) = "promoInfo/$promoId"
    }
}
