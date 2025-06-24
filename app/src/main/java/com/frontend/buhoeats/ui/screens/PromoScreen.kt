package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.models.Promo
import com.frontend.buhoeats.navigation.Screens
import com.frontend.buhoeats.ui.components.*
import com.frontend.buhoeats.ui.theme.AppColors
import com.frontend.buhoeats.ui.theme.ThemeManager
import com.frontend.buhoeats.viewmodel.PromoViewModel
import com.frontend.buhoeats.viewmodel.UserSessionViewModel

    @Composable
    fun PromoScreen(
        navController: NavHostController,
        userSessionViewModel: UserSessionViewModel,
        promoViewModel: PromoViewModel = viewModel()
    ) {
        val currentUser by userSessionViewModel.currentUser
        val restaurants = DummyData.getRestaurants()
        val isAdmin = currentUser?.rol == "admin"

        val adminRestaurant = if (isAdmin) {
            restaurants.find { it.admin == currentUser?.id }
        } else null

        val promosToLoad = if (isAdmin && adminRestaurant != null) {
            adminRestaurant.promos
        } else {
            restaurants.flatMap { it.promos }
        }

        if (promoViewModel.promos.isEmpty()) {
            promoViewModel.loadPromos(promosToLoad)
        }

        val backgroundImage = if (ThemeManager.isDarkTheme)
            painterResource(id = R.drawable.backgrounddark)
        else
            painterResource(id = R.drawable.backgroundlighttheme)

        Scaffold(
            topBar = {
                TopBar(
                    showBackIcon = true,
                    onNavClick = { navController.navigate(Screens.Home.route) }
                )
            },
            bottomBar = { BottomNavigationBar(navController) },

            floatingActionButton = {
                if (isAdmin && adminRestaurant != null) {
                    EditFloatingButton(onClick = {
                        val nuevoId = (adminRestaurant.promos.maxOfOrNull { it.id } ?: 0) + 1
                        navController.navigate(Screens.PromoInfo.createRoute(nuevoId, isNew = true))
                    })
                }
            }
            ,
            floatingActionButtonPosition = FabPosition.End
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Image(
                    painter = backgroundImage,
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

                    Text(
                        text = "Promociones",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.texto,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    promoViewModel.promos.forEach { promo ->
                        Box {
                            PromoCard(
                                promo = promo,
                                onClick = {
                                    navController.navigate(Screens.PromoInfo.createRoute(promo.id))
                                }
                            )

                            if (isAdmin) {
                                DeleteButton(
                                    onClick = {
                                        promoViewModel.deletePromo(promo)
                                    },
                                    modifier = Modifier
                                        .padding(vertical = 16.dp, horizontal = 8.dp)
                                        .align(Alignment.TopEnd)
                                )
                            }

                        }
                    }
                }
            }
        }
    }
