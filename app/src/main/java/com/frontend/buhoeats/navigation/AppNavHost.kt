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
                onNavigateToProfile = { navController.navigate("profileScreen") },
                onBack = { navController.popBackStack() }
            )

        }
        composable("profileScreen") {
            ProfileScreen(navController = navController, onBack = { navController.popBackStack() })
        }

        composable("editarPerfil") {
            myAccount(navController = navController, user = user)
        }
    }
}
