package com.frontend.buhoeats.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.ui.components.UserRowOptions
import com.frontend.buhoeats.ui.theme.AppColors
import com.frontend.buhoeats.ui.theme.ThemeManager
import com.frontend.buhoeats.utils.Translations
import com.frontend.buhoeats.viewmodel.BlockedUsersViewModel
import com.frontend.buhoeats.viewmodel.UserSessionViewModel

@Composable
fun BlockedUsersScreen(
    navController: NavController,
    userSessionViewModel: UserSessionViewModel,
    blockedUsersViewModel: BlockedUsersViewModel,
    restaurant: Restaurant
) {
    val currentUser = userSessionViewModel.currentUser.value
    val isAdminOfThisRestaurant = currentUser?.rol == "admin" && restaurant.admin == currentUser.id
    val context = LocalContext.current

    LaunchedEffect(restaurant) {
        if (isAdminOfThisRestaurant && restaurant.id.isNotBlank()) {
            blockedUsersViewModel.loadBlockedUsers(restaurant.id)
        }
    }

    val blockedUsers = blockedUsersViewModel.blockedUsers

    Scaffold(
        topBar = {
            TopBar(
                showBackIcon = true,
                onNavClick = { navController.popBackStack() }
            )
        },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            val backgroundImage = if (ThemeManager.isDarkTheme)
                painterResource(id = R.drawable.backgrounddark)
            else
                painterResource(id = R.drawable.backgroundlighttheme)

            Image(
                painter = backgroundImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            if (isAdminOfThisRestaurant) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        text = Translations.t("blocked_users_title"),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp),
                        color = AppColors.texto
                    )

                    if (blockedUsers.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                Translations.t("no_blocked_users"),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
                            )
                        }
                    } else {
                        LazyColumn {
                            items(blockedUsers) { user ->
                                UserRowOptions(
                                    user = user,
                                    onConfirmAction = {
                                        blockedUsersViewModel.unblockUser(
                                            restaurantId = restaurant.id,
                                            onUpdate = { updatedRestaurant ->
                                                Toast.makeText(
                                                    context,
                                                    Translations.t("user_unblocked"),
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                if (updatedRestaurant.id.isNotBlank()) {
                                                    blockedUsersViewModel.loadBlockedUsers(
                                                        updatedRestaurant.id
                                                    )
                                                }
                                            },
                                            user = user
                                        )
                                    }
                                )
                            }
                        }
                    }
                }

            } else {
                Text(
                    Translations.t("no_permission"),
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.texto
                )
            }
        }
    }
}
