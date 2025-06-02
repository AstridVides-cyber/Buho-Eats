package com.frontend.buhoeats.navigation

import Search
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.ui.screens.FavoriteScreen
import com.frontend.buhoeats.ui.screens.HomeScreen
import com.frontend.buhoeats.ui.screens.Login
import com.frontend.buhoeats.ui.screens.ProfileScreen
import com.frontend.buhoeats.ui.screens.RestaurantScreen
import com.frontend.buhoeats.ui.screens.SettingSlider
import com.frontend.buhoeats.ui.screens.SignUp
import com.frontend.buhoeats.ui.screens.MyAccount
import com.frontend.buhoeats.ui.screens.MapScreen
import com.frontend.buhoeats.ui.screens.PromoScreen
import com.frontend.buhoeats.ui.screens.PromoInfoScreen
import com.frontend.buhoeats.viewmodel.FavoritesViewModel
import com.frontend.buhoeats.viewmodel.FavoritesViewModelFactory
import com.frontend.buhoeats.viewmodel.UserSessionViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController) {
    val userSessionViewModel: UserSessionViewModel = viewModel()
    val currentUser = userSessionViewModel.currentUser.value
    val favoritesViewModel: FavoritesViewModel = viewModel(
        factory = FavoritesViewModelFactory(userSessionViewModel)
    )
    NavHost(navController = navController, startDestination = Screens.Login.route) {
        composable(Screens.Settings.route) {
            SettingSlider(
                navController = navController,
                onNavigateToProfile = { navController.navigate(Screens.Profile.route) },
                onBack = { navController.popBackStack() },
            )
        }
        composable(Screens.Profile.route) {
            ProfileScreen(
                onNavigateToAccount = { navController.navigate(Screens.MyAccount.route) },
                onBack = { navController.popBackStack() },
                navController = navController,
                userSessionViewModel = userSessionViewModel
            )
        }

        composable(Screens.MyAccount.route) {
            currentUser?.let {
                MyAccount(navController = navController, user = it, onBack = { navController.popBackStack() })
            }
        }

        composable(Screens.Login.route) {
            Login(navController,
                userSessionViewModel = userSessionViewModel
            )
        }

        composable(Screens.SignUp.route) {
            SignUp(navController = navController)
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
            val restaurantId = backStackEntry.arguments?.getInt("restaurantId")
            val restaurant = DummyData.getRestaurants().find { it.id == restaurantId }

            if (restaurant != null) {
                RestaurantScreen(
                    navController = navController,
                    restaurant = restaurant,
                    userSessionViewModel = userSessionViewModel
                )
            }
        }

        composable(Screens.Favorites.route) {
            FavoriteScreen(
                onRestaurantClick = { restaurantId ->
                    navController.navigate(Screens.Restaurant.createRoute(restaurantId))
                },
                onBack = {
                    navController.popBackStack()
                },
                navController = navController,
                userSessionViewModel = userSessionViewModel

            )
        }
        composable(Screens.Search.route) {
            Search(
                onBack = { navController.popBackStack() },
                onSearchResultClick = { restaurantName ->
                    val restaurant = DummyData.getRestaurants().find { it.name == restaurantName }
                    restaurant?.let {
                        navController.navigate(Screens.Restaurant.createRoute(it.id))
                    }
                },
                navController = navController
            )
        }
        composable(Screens.Map.route) {
           MapScreen(onBack = { navController.popBackStack() } , navController = navController
            )
        }
        composable(Screens.Promocion.route) {
            PromoScreen( navController = navController)
        }
        composable(
            route = Screens.PromoInfo.route,
            arguments = listOf(navArgument("promoId") { type = NavType.IntType })
        ) { backStackEntry ->
            val promoId = backStackEntry.arguments?.getInt("promoId")
            val allPromos = DummyData.getRestaurants().flatMap { it.promos }
            val promo = allPromos.find { it.id == promoId }
            val restaurant = DummyData.getRestaurants().find { it.promos.any { p -> p.id == promoId } }

            if (promo != null && restaurant != null) {
                PromoInfoScreen(
                    promo = promo,
                    restaurantName = restaurant.name,
                    contactInfo = restaurant.contactInfo,
                    navController = navController,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
