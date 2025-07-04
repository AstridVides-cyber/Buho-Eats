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
import com.frontend.buhoeats.navigation.Screens
import com.frontend.buhoeats.ui.components.*
import com.frontend.buhoeats.viewmodel.PromoViewModel
import com.frontend.buhoeats.viewmodel.RestaurantViewModel
import com.frontend.buhoeats.viewmodel.UserSessionViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.frontend.buhoeats.ui.theme.AppColors
import com.frontend.buhoeats.ui.theme.ThemeManager
import java.util.UUID
import com.frontend.buhoeats.utils.Translations

@Composable
fun PromoScreen(
    navController: NavHostController,
    userSessionViewModel: UserSessionViewModel,
    restaurantViewModel: RestaurantViewModel,
    promoViewModel: PromoViewModel = viewModel()
) {
    val currentUser by userSessionViewModel.currentUser
    val allRestaurants = restaurantViewModel.restaurantList
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

    val backgroundImage = if (ThemeManager.isDarkTheme)
        painterResource(id = R.drawable.backgrounddark)
    else
        painterResource(id = R.drawable.backgroundlighttheme)

    LaunchedEffect(currentUser, allRestaurants) {
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
                    val newPromoId = UUID.randomUUID().toString()
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
                painter = backgroundImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            if (promos.isEmpty() && promosToDisplay.isNotEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(Translations.t("loading_promotions"))
                }
            } else if (promos.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        if (adminRestaurant != null) Translations.t("no_promotions_yet")
                        else Translations.t("no_promotions_available")
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    Text(
                        text = Translations.t("promotions_title"),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.texto,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
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
                                            Toast.makeText(
                                                context,
                                                Translations.t("promotion_deleted"),
                                                Toast.LENGTH_SHORT
                                            ).show()
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
