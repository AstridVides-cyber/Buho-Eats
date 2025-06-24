package com.frontend.buhoeats.navigation


sealed class Screens(val route: String) {
    object Profile : Screens("profile")
    object EditAccount : Screens ("editaccount")
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
    object PromoInfo : Screens("promo_info/{promoId}?isNew={isNew}") {
        fun createRoute(promoId: Int, isNew: Boolean = false) =
            "promo_info/$promoId?isNew=$isNew"
    }
    object BlockedUser : Screens("blockedusers/{restaurantId}") {
        fun createRoute(restaurantId: Int) = "blockedusers/$restaurantId"
    }
    object EditRestaurant : Screens ("editrestaurant")
    object EditInfo : Screens ("edit_info")
    object Statistics : Screens("statistics")
    object ImagesRestaurant : Screens("imagerestaurant/{restaurantId}") {
        fun createRoute(id: Int) = "imagerestaurant/$id"
    }
    object EditMenu : Screens("editmenu")

    object RolAssign: Screens ("rolassign")
    object EditLocal : Screens("editlocal/{restaurantId}?isNew={isNew}") {
        fun createRoute(restaurantId: Int, isNew: Boolean): String {
            return "editlocal/$restaurantId?isNew=$isNew"
        }
    }
}
