package com.frontend.buhoeats.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.frontend.buhoeats.ui.screens.Login
import com.frontend.buhoeats.ui.screens.SignUp


@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login"){
        composable("login"){
            Login(navController)
        }
        composable("signUp") {
            SignUp(navController)
        }
    }



}