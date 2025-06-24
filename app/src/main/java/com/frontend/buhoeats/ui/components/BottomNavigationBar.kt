package com.frontend.buhoeats.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.navigation.Screens
import com.frontend.buhoeats.ui.theme.AppColors

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = AppColors.primary // ← Se adapta automáticamente
    ) {
        val items = listOf(
            Triple(R.drawable.hogar, "Inicio", Screens.Home.route),
            Triple(R.drawable.promociones, "Promociones", Screens.Promocion.route),
            Triple(R.drawable.mapa, "Mapa", Screens.Map.route),
            Triple(R.drawable.lupa, "Buscar", Screens.Search.route)
        )

        items.forEach { (icon, label, route) ->
            NavigationBarItem(
                selected = false,
                onClick = { navController.navigate(route) },
                icon = {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = { Text(label, color = Color.White) }, // texto adaptable
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppColors.secondary,
                    selectedTextColor = Color.White,
                    indicatorColor = AppColors.fondo,
                    unselectedIconColor = AppColors.texto,
                    unselectedTextColor = AppColors.texto
                )
            )
        }
    }
}
