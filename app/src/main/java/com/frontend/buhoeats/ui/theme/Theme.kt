package com.frontend.buhoeats.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF3D405B),
    secondary = Color(0xFF588B8B),
    background = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF1A1E3A),
    secondary = Color(0xFFA3D9A5),
    background = Color(0xFF121212),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White
)

@Composable
fun BuhoEatsTheme(darkTheme: Boolean = ThemeManager.isDarkTheme, content: @Composable () -> Unit) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}