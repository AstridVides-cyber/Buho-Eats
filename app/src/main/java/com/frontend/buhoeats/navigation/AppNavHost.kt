package com.frontend.buhoeats.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.frontend.buhoeats.ui.screen.SignUp
//import com.frontend.buhoeats.ui.screen.Login

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "signUp") {
        composable("signUp") {
            SignUp(navController)
        }
        /*composable("login") {
            Login(navController)
        }*/
    }



}