package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.data.InMemoryUserDataSource
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.RestaurantCard
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.ui.theme.ThemeManager
import com.frontend.buhoeats.utils.Translations.t
import com.frontend.buhoeats.viewmodel.FavoritesViewModel
import com.frontend.buhoeats.viewmodel.FavoritesViewModelFactory
import com.frontend.buhoeats.viewmodel.UserSessionViewModel

@Composable
fun FavoriteScreen(
    onRestaurantClick: (String) -> Unit,
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
    val allRestaurants = InMemoryUserDataSource.getRestaurants()
    val favoriteRestaurants = allRestaurants.filter { favoriteIds.contains(it.id) }

    val backgroundImage = if (ThemeManager.isDarkTheme)
        painterResource(id = R.drawable.backgrounddark)
    else
        painterResource(id = R.drawable.backgroundlighttheme)

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
                painter = backgroundImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            if (favoriteRestaurants.isEmpty()) {
                Text(
                    text = t("no_favorites_message"),
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
                            text = t("favorites"),
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
