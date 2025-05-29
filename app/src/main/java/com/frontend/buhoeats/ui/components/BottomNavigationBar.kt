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

@Composable
fun BottomNavigationBar(navController : NavController) {
    NavigationBar(
        containerColor = Color(0xFF3D405B)
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screens.Home.route) },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.hogar),
                    contentDescription = "Inicio",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Inicio") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1B1D2A),
                selectedTextColor = Color(0xFF1B1D2A),
                indicatorColor = Color(0xFF2F3245),
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White
            )
        )

        NavigationBarItem(
            selected = false,
            onClick = {navController.navigate(Screens.Promocion.route)  },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.promociones),
                    contentDescription = "Promociones",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Promociones") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1B1D2A),
                selectedTextColor = Color(0xFF1B1D2A),
                indicatorColor = Color(0xFF2F3245),
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White
            )
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(Screens.Map.route)},
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.mapa),
                    contentDescription = "Mapa",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Mapa") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1B1D2A),
                selectedTextColor = Color(0xFF1B1D2A),
                indicatorColor = Color(0xFF2F3245),
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White
            )
        )

        NavigationBarItem(
            selected = false,
            onClick = {  },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.lupa),
                    contentDescription = "Buscar",
                    modifier = Modifier.size(24.dp)
                )
            },
            label = { Text("Buscar") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFF1B1D2A),
                selectedTextColor = Color(0xFF1B1D2A),
                indicatorColor = Color(0xFF2F3245),
                unselectedIconColor = Color.White,
                unselectedTextColor = Color.White
            )
        )
    }

}