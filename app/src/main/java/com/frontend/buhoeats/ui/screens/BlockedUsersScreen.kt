package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import androidx.navigation.NavController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.ui.components.UserRowOptions
import com.frontend.buhoeats.viewmodel.UserSessionViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.frontend.buhoeats.viewmodel.BlockedUsersViewModel


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
        if (isAdminOfThisRestaurant) {
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
            Image(
                painter = painterResource(id = R.drawable.backgroundlighttheme),
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
                        text = "Usuarios bloqueados:",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    if (blockedUsers.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No hay usuarios bloqueados por ahora",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.DarkGray
                            )
                        }
                    } else {
                        LazyColumn {
                            items(blockedUsers) { user ->
                                UserRowOptions(
                                    user = user,
                                    onConfirmAction = {
                                        blockedUsersViewModel.unblockUser(
                                            user,
                                            restaurantId = restaurant.id,
                                            onUpdate = { updatedRestaurant ->
                                                Toast.makeText(context, "Usuario desbloqueado exitosamente", Toast.LENGTH_SHORT).show()
                                                blockedUsersViewModel.loadBlockedUsers(updatedRestaurant.id)
                                            }
                                        )
                                    }
                                )
                            }
                        }
                    }
                }

                } else {
                Text(
                    "No tienes permisos para ver esta pantalla",
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}