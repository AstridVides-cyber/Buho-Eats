package com.frontend.buhoeats.navigation

import Search
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.frontend.buhoeats.models.Promo
import com.frontend.buhoeats.ui.screens.BlockedUsersScreen
import com.frontend.buhoeats.ui.screens.EditInfoAdmin
import com.frontend.buhoeats.ui.screens.EditImageScreen
import com.frontend.buhoeats.ui.screens.EditLocalScreen
import com.frontend.buhoeats.ui.screens.EditMenuScreen
import com.frontend.buhoeats.ui.screens.EditRestaurantScreen
import com.frontend.buhoeats.ui.screens.FavoriteScreen
import com.frontend.buhoeats.ui.screens.HomeScreen
import com.frontend.buhoeats.ui.screens.Login
import com.frontend.buhoeats.ui.screens.ProfileScreen
import com.frontend.buhoeats.ui.screens.RestaurantScreen
import com.frontend.buhoeats.ui.screens.SignUp
import com.frontend.buhoeats.ui.screens.EditAccountScreen
import com.frontend.buhoeats.ui.screens.MapScreen
import com.frontend.buhoeats.ui.screens.PromoScreen
import com.frontend.buhoeats.ui.screens.PromoInfoScreen
import com.frontend.buhoeats.ui.screens.RolAssign
import com.frontend.buhoeats.ui.screens.StatisticsScreen
import com.frontend.buhoeats.viewmodel.BlockedUsersViewModel
import com.frontend.buhoeats.viewmodel.PromoViewModel
import com.frontend.buhoeats.viewmodel.RestaurantViewModel
import com.frontend.buhoeats.viewmodel.UserSessionViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController) {
    val userSessionViewModel: UserSessionViewModel = viewModel()
    val currentUser = userSessionViewModel.currentUser.value
    val restaurantViewModel: RestaurantViewModel = viewModel()
    val promoViewModel: PromoViewModel = viewModel()
    val blockedUsersViewModel: BlockedUsersViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screens.Login.route) {
        composable(Screens.Profile.route) {
            ProfileScreen(
                onNavigateToAccount = { navController.navigate(Screens.EditAccount.route) },
                onBack = { navController.popBackStack() },
                navController = navController,
                userSessionViewModel = userSessionViewModel
            )
        }

        composable(Screens.EditAccount.route) {
            currentUser?.let {
                EditAccountScreen(
                    navController = navController,
                    onBack = { navController.popBackStack() },
                    userSessionViewModel = userSessionViewModel)
            }
        }

        composable(Screens.Login.route) {
            Login(
                navController,
                userSessionViewModel = userSessionViewModel
            )
        }

        composable(Screens.SignUp.route) {
            SignUp(navController = navController,
                userSessionViewModel = userSessionViewModel)
        }
        composable(Screens.Home.route) {
            HomeScreen(
                navController = navController,
                userSessionViewModel = userSessionViewModel,
                onRestaurantClick = { id ->
                    navController.navigate(Screens.Restaurant.createRoute(id))
                },
                restaurantViewModel = restaurantViewModel
            )
        }

        composable(
            route = Screens.Restaurant.route,
            arguments = listOf(navArgument("restaurantId") { type = NavType.StringType })
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getString("restaurantId")
            val restaurant = restaurantViewModel.restaurantList.find { it.id == restaurantId }

            if (restaurant != null) {
                RestaurantScreen(
                    navController = navController,
                    restaurant = restaurant,
                    userSessionViewModel = userSessionViewModel,
                    restaurantViewModel = restaurantViewModel
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
                userSessionViewModel = userSessionViewModel,
                restaurantViewModel = restaurantViewModel

            )
        }
        composable(Screens.Search.route) {
            Search(
                onBack = { navController.navigate(Screens.Home.route) },
                onSearchResultClick = { restaurantName ->
                    val searchedRestaurant = restaurantViewModel.restaurantList.find { it.name == restaurantName }
                    searchedRestaurant?.let {
                        navController.navigate(Screens.Restaurant.createRoute(it.id))
                    }
                },
                navController = navController,
                userSessionViewModel = userSessionViewModel,
                restaurantViewModel = restaurantViewModel
            )
        }
        composable(Screens.Map.route) {
            MapScreen(
                onBack = { navController.navigate(Screens.Home.route) }, navController = navController,
                restaurantViewModel = restaurantViewModel,
            )
        }

        composable(Screens.Promocion.route) {
            PromoScreen(
                navController = navController,
                userSessionViewModel = userSessionViewModel,
                promoViewModel = promoViewModel,
                restaurantViewModel = restaurantViewModel
            )
        }

        composable(
            route = Screens.BlockedUser.route,
            arguments = listOf(navArgument("restaurantId") { type = NavType.StringType })
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getString("restaurantId")
            val restaurant = restaurantViewModel.restaurantList.find { it.id == restaurantId }

            if (restaurant != null) {
                BlockedUsersScreen(
                    navController = navController,
                    userSessionViewModel = userSessionViewModel,
                    blockedUsersViewModel = blockedUsersViewModel,
                    restaurant = restaurant
                )
            }
        }
        composable(Screens.EditRestaurant.route) {
            val currentUser = userSessionViewModel.currentUser.value
            val restaurant = restaurantViewModel.restaurantList
                .find { it.admin == currentUser?.id }

            if (restaurant != null) {
                EditRestaurantScreen(
                    navController = navController,
                    restaurant = restaurant,
                    onBack = { navController.popBackStack() },
                    onEditImages = { restaurantId ->
                        navController.navigate(Screens.ImagesRestaurant.createRoute(restaurantId))
                    },
                    onEditInfo = { navController.navigate(Screens.EditInfo.route) },
                    onEditMenu = { navController.navigate(Screens.EditMenu.route) }
                )
            }
        }

        composable(Screens.EditInfo.route) {
            EditInfoAdmin(
                navController = navController,
                userSessionViewModel = userSessionViewModel,
                restaurantViewModel = restaurantViewModel
            )
        }


        composable(Screens.Statistics.route) {
            currentUser?.let { user ->
                val restaurant = restaurantViewModel.restaurantList.find { it.admin == user.id }
                if (restaurant != null) {
                    StatisticsScreen(
                        navController = navController,
                        restaurant = restaurant,
                        onBack = { navController.popBackStack() },
                        blockedUsersViewModel = blockedUsersViewModel,
                        userSessionViewModel = userSessionViewModel,
                        restaurantViewModel = restaurantViewModel)
                }
            }
        }

        composable(
            route = Screens.ImagesRestaurant.route,
            arguments = listOf(navArgument("restaurantId") { type = NavType.StringType })
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getString("restaurantId")
            val restaurant = restaurantViewModel.restaurantList.find { it.id == restaurantId }

            if (restaurant != null) {
                EditImageScreen(
                    navController = navController,
                    restaurant = restaurant,
                    userSessionViewModel = userSessionViewModel,
                    restaurantViewModel = restaurantViewModel
                )
            }
        }

        composable(
            route = Screens.PromoInfo.route,
            arguments = listOf(
                navArgument("promoId") { type = NavType.StringType },
                navArgument("isNew") { type = NavType.BoolType; defaultValue = false }
            )
        ) { backStackEntry ->
            val promoId = backStackEntry.arguments?.getString("promoId") ?: "-1"
            val isNew = backStackEntry.arguments?.getBoolean("isNew") ?: false
            val promos by promoViewModel.promos.collectAsState()

            val promo = if (isNew) {
                Promo(
                    id = "-1", // Placeholder for new promo
                    name = "",
                    description = "",
                    promprice = "",
                    price = "",
                    imageUrl = "",
                    reglas = "",
                    restaurantId = ""
                )
            } else {
                promos.find { it.id == promoId } ?: return@composable
            }

            val restaurant =
                restaurantViewModel.restaurantList.find { it.promos.any { promo -> promo.id == promoId } }
                    ?: restaurantViewModel.restaurantList.find { it.admin == userSessionViewModel.currentUser.value?.id }

            val isAdmin = userSessionViewModel.currentUser.value?.rol == "admin"

            if (restaurant != null) {
                PromoInfoScreen(
                    promo = promo,
                    restaurantName = restaurant.name,
                    contactInfo = restaurant.contactInfo,
                    navController = navController,
                    onBackClick = { navController.popBackStack() },
                    isAdmin = isAdmin,
                    promoViewModel = promoViewModel,
                    userSessionViewModel = userSessionViewModel
                )
            }
        }

        composable(Screens.EditMenu.route) {
            EditMenuScreen(
                navController = navController,
                userSessionViewModel = userSessionViewModel,
                restaurantViewModel = restaurantViewModel
            )
        }
        composable(
            route = Screens.EditLocal.route,
            arguments = listOf(
                navArgument("restaurantId") { type = NavType.StringType },
                navArgument("isNew") { type = NavType.BoolType; defaultValue = false }
            )
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getString("restaurantId") ?: "-1"
            val isNew = backStackEntry.arguments?.getBoolean("isNew") ?: false
            val restaurant = restaurantViewModel.restaurantList.find { it.id == restaurantId }

            EditLocalScreen(
                isNewLocal = isNew,
                restaurant = restaurant,
                navController = navController,
                onBackClick = { navController.popBackStack() },
                restaurantViewModel = restaurantViewModel,
                userSessionViewModel = userSessionViewModel
            )
        }

        composable (Screens.RolAssign.route){
            RolAssign(
                navController = navController,
                userSessionViewModel = userSessionViewModel
            )
        }

    }
}

