package com.frontend.buhoeats.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.frontend.buhoeats.ui.screen.singUp

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login"){
        composable("singUp"){
            singUp(navController)
        }
    }


}