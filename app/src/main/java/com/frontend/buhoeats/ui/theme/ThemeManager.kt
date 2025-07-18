package com.frontend.buhoeats.ui.theme

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

object ThemeManager {
    var isDarkTheme by mutableStateOf(false)

    fun toggleTheme(toDark: Boolean) {
        isDarkTheme = toDark
    }
}