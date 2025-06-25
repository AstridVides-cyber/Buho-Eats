package com.frontend.buhoeats.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object CustomLightColors {
    val primary = Color(0xFF3D405B)
    val secondary = Color(0xFF588B8B)
    val fondo = Color(0xFFFFFFFF)
    val texto = Color(0xFF000000)

    val text = Color(0xFFFFFFFF)

    val accent = Color(0xFF747BB8)
}

object CustomDarkColors {
    val primary = Color(0xFF1A1E3A)
    val secondary = Color(0xFFA3D9A5)
    val fondo = Color(0xFF121212)
    val texto = Color(0xFFFFFFFF)
    val text = Color(0xFF000000)


    val accent = Color(0xFF6272FB)
}

object AppColors {
    val primary: Color
        @Composable get() = if (ThemeManager.isDarkTheme) CustomDarkColors.primary else CustomLightColors.primary

    val secondary: Color
        @Composable get() = if (ThemeManager.isDarkTheme) CustomDarkColors.secondary else CustomLightColors.secondary

    val fondo: Color
        @Composable get() = if (ThemeManager.isDarkTheme) CustomDarkColors.fondo else CustomLightColors.fondo

    val texto: Color
        @Composable get() = if (ThemeManager.isDarkTheme) CustomDarkColors.texto else CustomLightColors.texto

    val text: Color
        @Composable get() = if (ThemeManager.isDarkTheme) CustomDarkColors.text else CustomLightColors.text

    val accent : Color
        @Composable get() = if (ThemeManager.isDarkTheme) CustomDarkColors.accent else CustomLightColors.accent

}
