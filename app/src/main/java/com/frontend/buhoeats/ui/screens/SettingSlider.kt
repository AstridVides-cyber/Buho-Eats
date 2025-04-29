package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingSlider() {
    ModalDrawerSheet {
        Text("Opciones de usuario", modifier = Modifier.padding(16.dp))
        HorizontalDivider()
        Text("Perfil", modifier = Modifier.padding(16.dp))
        Text("Ajustes", modifier = Modifier.padding(16.dp))
        Text("Cerrar sesión", modifier = Modifier.padding(16.dp))
    }
}