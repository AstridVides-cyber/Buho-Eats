package com.frontend.buhoeats

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.frontend.buhoeats.navigation.AppNavHost
import com.frontend.buhoeats.ui.theme.BuhoEatsTheme
import com.frontend.buhoeats.ui.theme.ThemeManager
import com.frontend.buhoeats.viewmodel.RestaurantViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BuhoEatsTheme(darkTheme = ThemeManager.isDarkTheme) {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }

}
