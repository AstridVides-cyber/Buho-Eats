package com.frontend.buhoeats.navigation

<<<<<<< HEAD
=======

>>>>>>> feature/signUp
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
<<<<<<< HEAD
import com.frontend.buhoeats.ui.screens.Login
=======
import com.frontend.buhoeats.ui.screens.SignUp
//import com.frontend.buhoeats.ui.screen.Login
>>>>>>> feature/signUp

@Composable
fun AppNavigator() {
    val navController = rememberNavController()

<<<<<<< HEAD
    NavHost(navController = navController, startDestination = "login"){
        composable("login"){
            Login(navController)
        }
    }

=======
    NavHost(navController = navController, startDestination = "signUp") {
        composable("signUp") {
            SignUp(navController)
        }
        /*composable("login") {
            Login(navController)*/

    }



>>>>>>> feature/signUp
}