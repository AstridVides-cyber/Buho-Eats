package com.frontend.buhoeats.navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.frontend.buhoeats.ui.screens.Login
import com.frontend.buhoeats.ui.screens.SignUp


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.Login.route) {
        composable(Screens.Login.route) {
            Login(navController)
        }
        composable(Screens.SignUp.route) {
            SignUp(navController)
        }

    }
}