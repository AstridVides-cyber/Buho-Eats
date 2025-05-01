package com.frontend.buhoeats.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavHostController
import com.frontend.buhoeats.R

val montserratFontFamily = FontFamily(
    Font(R.font.montserrat_bold)
)

@Composable
fun Login(navControl: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


}