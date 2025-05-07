package com.frontend.buhoeats

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.frontend.buhoeats.ui.theme.BuhoEatsTheme


import com.frontend.buhoeats.navigation.AppNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BuhoEatsTheme {
                AppNavigator()
                    }
            }
        }
    }



