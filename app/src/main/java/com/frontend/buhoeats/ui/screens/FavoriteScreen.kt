package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.R
import com.frontend.buhoeats.ui.components.RestaurantCard
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.viewmodel.FavoritesViewModel
import com.frontend.buhoeats.viewmodel.FavoritesViewModelFactory
import com.frontend.buhoeats.viewmodel.UserSessionViewModel

@Composable
fun FavoriteScreen(
    onRestaurantClick: (Int) -> Unit,
    onBack: () -> Unit = {},
    navController: NavController,
    userSessionViewModel: UserSessionViewModel,
    favoritesViewModel: FavoritesViewModel = viewModel(
        factory = FavoritesViewModelFactory(userSessionViewModel)
    )
) {
    val favoriteIds by favoritesViewModel.favoriteRestaurantIds.collectAsState()
    LaunchedEffect(Unit) {
        favoritesViewModel.refreshFavorites()
    }
    val allRestaurants = DummyData.getRestaurants()
    val favoriteRestaurants = allRestaurants.filter { restaurant ->
        favoriteIds.contains(restaurant.id)
    }

    Scaffold(
        topBar = {
            TopBar(
                showBackIcon = true,
                onNavClick = onBack
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.backgroundlighttheme),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            if (favoriteRestaurants.isEmpty()) {
                Text(
                    text = "No has añadido ningún restaurante a favoritos.",
                    fontSize = 20.sp,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    item {
                        Text(
                            text = "Favoritos",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )
                    }

                    items(favoriteRestaurants) { restaurant ->
                        RestaurantCard(restaurant = restaurant) {
                            onRestaurantClick(restaurant.id)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

