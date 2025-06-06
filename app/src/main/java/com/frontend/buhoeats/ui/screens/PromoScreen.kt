package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.navigation.Screens
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.DeleteButton
import com.frontend.buhoeats.ui.components.EditFloatingButton
import com.frontend.buhoeats.ui.components.PromoCard
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.viewmodel.UserSessionViewModel

@Composable
fun PromoScreen(
    navController: NavHostController,
    userSessionViewModel: UserSessionViewModel
) {
    val currentUser by userSessionViewModel.currentUser
    val restaurants = DummyData.getRestaurants()
    val isAdmin = currentUser?.rol == "admin"

    // Buscar el restaurante que administra el usuario (si es admin)
    val adminRestaurant = if (isAdmin) {
        restaurants.find { it.admin == currentUser?.id }
    } else null

    // Filtrar promociones según el rol
    val allPromos = if (isAdmin && adminRestaurant != null) {
        adminRestaurant.promos
    } else {
        restaurants.flatMap { it.promos }
    }

    Scaffold(
        topBar = {
            TopBar(
                showBackIcon = true,
                onNavClick = { navController.popBackStack() }
            )
        },
        bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = {
            if (isAdmin && adminRestaurant != null) {
                EditFloatingButton(onClick = {
                    navController.navigate("pantalla_editar_promos")
                })
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = painterResource(id = R.drawable.backgroundlighttheme),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                allPromos.forEach { promo ->
                    Box {
                        PromoCard(
                            promo = promo,
                            onClick = {
                                navController.navigate(Screens.PromoInfo.createRoute(promo.id))
                            }
                        )

                        if (isAdmin && adminRestaurant?.promos?.contains(promo) == true) {
                            DeleteButton(
                                onClick = {
                                    // Lógica de eliminación de promo
                                },
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
