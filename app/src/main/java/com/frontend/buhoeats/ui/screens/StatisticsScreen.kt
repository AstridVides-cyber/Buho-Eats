package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Block
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.frontend.buhoeats.R
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.models.User
import com.frontend.buhoeats.ui.components.ConfirmationDialog
import com.frontend.buhoeats.viewmodel.BlockedUsersViewModel
import com.frontend.buhoeats.viewmodel.RestaurantViewModel

@Composable
fun StatisticsScreen(
    navController: NavController,
    restaurant: Restaurant,
    onBack: () -> Unit,
    blockedUsersViewModel: BlockedUsersViewModel
) {
    var currentRestaurant by remember { mutableStateOf(restaurant) }
    var showDialog by remember { mutableStateOf(false) }
    var userToBlock by remember { mutableStateOf<User?>(null) }

    Scaffold(
        topBar = {
            TopBar(
                showBackIcon = true,
                onNavClick = onBack
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
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

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp)
            ) {
                val nonBlockedComments = currentRestaurant.comments.filter { comment ->
                    comment.userId !in currentRestaurant.blockedUsers
                }

                items(nonBlockedComments) { comment ->
                    val user = DummyData.getUsers().find { it.id == comment.userId }
                    val rating = currentRestaurant.ratings.find { it.userId == comment.userId }

                    Card(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.AccountCircle,
                                    contentDescription = "Usuario",
                                    tint = Color.Black,
                                    modifier = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))

                                val displayName = user?.let { "${it.name} ${it.lastName}" } ?: "Usuario desconocido"

                                Text(
                                    text = displayName,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.weight(1f))

                                IconButton(onClick = {
                                    userToBlock = user
                                    showDialog = true
                                }) {
                                    Icon(Icons.Default.Block, contentDescription = null, tint = Color.Red)
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(text = comment.comment, fontSize = 16.sp, color = Color.DarkGray)
                            Spacer(modifier = Modifier.height(8.dp))

                            rating?.let {
                                Row {
                                    repeat(it.rating) {
                                        Icon(
                                            Icons.Default.Star,
                                            contentDescription = null,
                                            tint = Color(0xFFFFC107),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (showDialog && userToBlock != null) {
                ConfirmationDialog(
                    message = "¿Estás seguro que deseas bloquear a este usuario?",
                    onConfirm = {
                        blockedUsersViewModel.blockUser(
                            user = userToBlock!!,
                            restaurant = currentRestaurant,
                            onUpdate = { updatedRestaurant ->
                                DummyData.updateRestaurant(updatedRestaurant)
                                currentRestaurant = updatedRestaurant
                                blockedUsersViewModel.loadBlockedUsers(updatedRestaurant)
                            }
                        )
                        showDialog = false
                        userToBlock = null
                    },
                    onDismiss = {
                        showDialog = false
                        userToBlock = null
                    }
                )
            }
        }
    }
}