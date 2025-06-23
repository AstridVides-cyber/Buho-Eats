package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import android.widget.Toast
import androidx.navigation.NavHostController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.data.InMemoryUserDataSource
import com.frontend.buhoeats.navigation.Screens
import com.frontend.buhoeats.ui.components.*
import com.frontend.buhoeats.viewmodel.PromoViewModel
import com.frontend.buhoeats.viewmodel.UserSessionViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState

@Composable
fun PromoScreen(
    navController: NavHostController,
    userSessionViewModel: UserSessionViewModel,
    promoViewModel: PromoViewModel = viewModel()
) {
    val currentUser by userSessionViewModel.currentUser
    val allRestaurants = InMemoryUserDataSource.getRestaurants()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val adminRestaurant = if (currentUser?.rol == "admin") {
        allRestaurants.firstOrNull { it.admin == currentUser!!.id }
    } else null

    val promos by promoViewModel.promos.collectAsState()

    val promosToDisplay = when (currentUser?.rol) {
        "admin" -> promos.filter { it.restaurantId == adminRestaurant?.id }
        else -> promos
    }

    LaunchedEffect(currentUser) {
        promoViewModel.loadPromosForUser(currentUser)
    }

    Scaffold(
        topBar = {
            TopBar(
                showBackIcon = true,
                onNavClick = { navController.navigate(Screens.Home.route) }
            )
        },
        bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = {
            if (adminRestaurant != null) {
                EditFloatingButton(onClick = {
                    val maxExistingPromoId = promos.maxOfOrNull { it.id } ?: 0
                    val newPromoId = maxExistingPromoId + 1
                    navController.navigate(Screens.PromoInfo.createRoute(newPromoId, isNew = true))
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

            if (promos.isEmpty() && promosToDisplay.isNotEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Cargando promociones...")
                }
            } else if (promos.isEmpty() && promosToDisplay.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        if (adminRestaurant != null) "Tu restaurante aún no tiene promociones."
                        else "No hay promociones disponibles en este momento."
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    promosToDisplay.forEach { promo ->
                        Box {
                            PromoCard(
                                promo = promo,
                                onClick = {
                                    navController.navigate(Screens.PromoInfo.createRoute(promo.id))
                                }
                            )

                            if (adminRestaurant != null && promo.restaurantId == adminRestaurant.id) {
                                DeleteButton(
                                    onClick = {
                                        promoViewModel.deletePromo(promo, currentUser)
                                        scope.launch {
                                            Toast.makeText(context, "Promoción eliminada", Toast.LENGTH_SHORT).show()
                                        }
                                    },
                                    modifier = Modifier
                                        .padding(vertical = 16.dp, horizontal = 8.dp)
                                        .align(Alignment.TopEnd)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}