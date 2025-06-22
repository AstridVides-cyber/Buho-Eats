package com.frontend.buhoeats.navigation

import Search
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.models.Promo
import com.frontend.buhoeats.ui.screens.BlockedUsersScreen
import com.frontend.buhoeats.ui.screens.EditInfo
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
                    user = it,
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
            arguments = listOf(navArgument("restaurantId") { type = NavType.IntType })
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getInt("restaurantId")
            val restaurant = DummyData.getRestaurants().find { it.id == restaurantId }

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
                userSessionViewModel = userSessionViewModel

            )
        }
        composable(Screens.Search.route) {
            Search(
                onBack = { navController.navigate(Screens.Home.route) },
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
            MapScreen(
                onBack = { navController.navigate(Screens.Home.route) }, navController = navController
            )
        }

        composable(Screens.Promocion.route) {
            PromoScreen(
                navController = navController,
                userSessionViewModel = userSessionViewModel,
                promoViewModel = promoViewModel
            )
        }

        composable(
            route = Screens.BlockedUser.route,
            arguments = listOf(navArgument("restaurantId") { type = NavType.IntType })
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getInt("restaurantId")
            val restaurant = DummyData.getRestaurants().find { it.id == restaurantId }

            if (restaurant != null) {
                BlockedUsersScreen(
                    navController = navController,
                    userSessionViewModel = userSessionViewModel,
                    blockeUsersViewModel = blockedUsersViewModel,
                    restaurant = restaurant
                )
            }
        }
        composable(Screens.EditRestaurant.route) {
            val restaurant = DummyData.getRestaurants().first()

            EditRestaurantScreen(
                navController = navController,
                restaurant = restaurant,
                onBack = { navController.popBackStack() },
                onEditImages = { restaurantId ->
                    navController.navigate(Screens.ImagesRestaurant.createRoute(restaurantId))
                },
                onEditInfo = { navController.navigate(Screens.EditInfo.route)  },
                onEditMenu = { navController.navigate(Screens.EditMenu.route) }
            )
        }

        composable(Screens.EditInfo.route) {
            EditInfo(
                navController = navController,
                userSessionViewModel = userSessionViewModel,
                restaurantViewModel = restaurantViewModel
            )
        }


        composable(Screens.Statistics.route) {
            currentUser?.let { user ->
                val restaurant = DummyData.getRestaurants().find { it.admin == user.id }
                if (restaurant != null) {
                    StatisticsScreen(
                        navController = navController,
                        restaurant = restaurant,
                        onBack = { navController.popBackStack() },
                        blockedUsersViewModel = blockedUsersViewModel)
                }
            }
        }

        composable(
            route = Screens.ImagesRestaurant.route,
            arguments = listOf(navArgument("restaurantId") { type = NavType.IntType })
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getInt("restaurantId")
            val restaurant = DummyData.getRestaurants().find { it.id == restaurantId }

            if (restaurant != null) {
                EditImageScreen(
                    navController = navController,
                    restaurant = restaurant,
                    userSessionViewModel = userSessionViewModel
                )
            }
        }

        composable(
            route = Screens.PromoInfo.route,
            arguments = listOf(
                navArgument("promoId") { type = NavType.IntType },
                navArgument("isNew") { type = NavType.BoolType; defaultValue = false }
            )
        ) { backStackEntry ->
            val promoId = backStackEntry.arguments?.getInt("promoId") ?: -1
            val isNew = backStackEntry.arguments?.getBoolean("isNew") ?: false

            val promo = if (isNew) {
                Promo(
                    id = promoId,
                    name = "",
                    description = "",
                    promprice = "",
                    price = "",
                    imageUrl = "",
                    reglas = ""
                )
            } else {
                promoViewModel.promos.find { it.id == promoId } ?: return@composable
            }

            val restaurant =
                DummyData.getRestaurants().find { it.promos.any { it.id == promoId } }
                    ?: DummyData.getRestaurants()
                        .find { it.admin == userSessionViewModel.currentUser.value?.id }

            val isAdmin = userSessionViewModel.currentUser.value?.rol == "admin"

            if (restaurant != null) {
                PromoInfoScreen(
                    promo = promo,
                    restaurantName = restaurant.name,
                    contactInfo = restaurant.contactInfo,
                    navController = navController,
                    onBackClick = { navController.popBackStack() },
                    isAdmin = isAdmin,
                    promoViewModel = promoViewModel
                )
            }
        }

        composable(Screens.EditMenu.route) {
            EditMenuScreen(
                navController = navController,
                userSessionViewModel = userSessionViewModel
            )
        }
        composable(
            route = Screens.EditLocal.route,
            arguments = listOf(
                navArgument("restaurantId") { type = NavType.IntType },
                navArgument("isNew") { type = NavType.BoolType; defaultValue = false }
            )
        ) { backStackEntry ->
            val restaurantId = backStackEntry.arguments?.getInt("restaurantId") ?: -1
            val isNew = backStackEntry.arguments?.getBoolean("isNew") ?: false
            val restaurant = DummyData.getRestaurants().find { it.id == restaurantId }

            EditLocalScreen(
                isNewLocal = isNew,
                restaurant = restaurant,
                navController = navController,
                onBackClick = { navController.popBackStack() },
                restaurantViewModel = restaurantViewModel
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

