package com.frontend.buhoeats.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.ui.screens.MenuScreen
import com.frontend.buhoeats.ui.screens.RestauranteScreen
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val restaurantList = DummyData.getRestaurants()

    NavHost(navController = navController, startDestination = Screen.Menu.route) {
        composable(Screen.Menu.route) {
            MenuScreen(restaurantList = restaurantList, onRestaurantClick = { restaurantId ->
                navController.navigate(Screen.Restaurant.createRoute(restaurantId))
            })
        }
        composable(
            route = Screen.Restaurant.route,
            arguments = listOf(navArgument("restaurantId") { type = NavType.IntType })
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getInt("restaurantId") ?: 0
            val selectedRestaurant = restaurantList.find { it.id == restaurantId }
            selectedRestaurant?.let {
                RestauranteScreen(navController = navController, restaurant = it)
            }
        }
    }
}