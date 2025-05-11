package com.frontend.buhoeats.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.ui.screens.ProfileScreen
import com.frontend.buhoeats.ui.screens.SettingSlider
import com.frontend.buhoeats.ui.screens.myAccount

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController) {
    val user = DummyData.getUser()

    NavHost(navController = navController, startDestination = Screens.Settings.route) {
        composable(Screens.Settings.route) {
            SettingSlider(
                onNavigateToProfile = { navController.navigate(Screens.Profile.route) },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screens.Profile.route) {
            ProfileScreen(onBack = { navController.popBackStack() })
        }
        composable("profile") {
            myAccount(navController, user)
        }
    }
}
