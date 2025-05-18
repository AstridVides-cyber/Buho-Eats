package com.frontend.buhoeats.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.ui.screens.HomeScreen
import com.frontend.buhoeats.ui.screens.Login
import com.frontend.buhoeats.ui.screens.ProfileScreen
import com.frontend.buhoeats.ui.screens.RestaurantScreen
import com.frontend.buhoeats.ui.screens.SettingSlider
import com.frontend.buhoeats.ui.screens.SignUp
import com.frontend.buhoeats.ui.screens.MyAccount
import com.frontend.buhoeats.ui.screens.PromoScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController) {
    val user = DummyData.getUser()
    val restaurantList = DummyData.getRestaurants()

    NavHost(navController = navController, startDestination = Screens.Login.route) {
        composable(Screens.Settings.route) {
            SettingSlider(
                onNavigateToProfile = { navController.navigate(Screens.Profile.route) },
                onBack = { navController.popBackStack()},
                navController
            )

        }
        composable(Screens.Profile.route) {
            ProfileScreen(
                onNavigateToAccount = { navController.navigate(Screens.MyAccount.route) },
                onBack = { navController.popBackStack() },
                navController
            )
        }
        composable(Screens.MyAccount.route) {
            MyAccount(navController = navController, user = user , onBack = { navController.popBackStack() }
            )
        }
        composable(Screens.Login.route) {
            Login(navController)
        }
        composable(Screens.SignUp.route) {
            SignUp(navController)
        }
        composable(Screens.Home.route) {
            HomeScreen(
                navController = navController,
                onRestaurantClick = { id ->
                    navController.navigate(Screens.Restaurant.createRoute(id))
                }
            )
        }
        composable(
            route = Screens.Restaurant.route,
            arguments = listOf(navArgument("restaurantId") { type = NavType.IntType })
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getInt("restaurantId") ?: 0
            val selectedRestaurant = restaurantList.find { it.id == restaurantId }
            selectedRestaurant?.let {
                RestaurantScreen(navController = navController, restaurant = it)
            }
        }
        composable(Screens.Promocion.route) {
            PromoScreen(navController)
        }
    }
}
