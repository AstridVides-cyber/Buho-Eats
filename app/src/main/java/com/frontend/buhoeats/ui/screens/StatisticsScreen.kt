package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.frontend.buhoeats.R
import com.frontend.buhoeats.viewmodel.BlockedUsersViewModel
import com.frontend.buhoeats.viewmodel.RestaurantViewModel
import com.frontend.buhoeats.viewmodel.UserSessionViewModel
import android.widget.Toast
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import coil.compose.AsyncImage
import com.frontend.buhoeats.models.User
import com.frontend.buhoeats.ui.components.ConfirmationDialog
import com.frontend.buhoeats.ui.theme.ThemeManager
import com.frontend.buhoeats.utils.ImageConverter
import androidx.compose.ui.graphics.asImageBitmap
import com.frontend.buhoeats.utils.Translations
import androidx.compose.foundation.lazy.items

@Composable
fun StatisticsScreen(
    navController: NavController,
    restaurant: Restaurant,
    onBack: () -> Unit,
    blockedUsersViewModel: BlockedUsersViewModel,
    restaurantViewModel: RestaurantViewModel,
    userSessionViewModel: UserSessionViewModel
) {
    var currentRestaurant by remember { mutableStateOf(restaurant) }
    var showDialog by remember { mutableStateOf(false) }
    var userToBlock by remember { mutableStateOf<User?>(null) }
    val context = LocalContext.current

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
                painter = backgroundImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Ya no es necesario filtrar comentarios, se usan todos los comentarios actuales
            val comments = currentRestaurant.comments

            if (comments.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = Translations.t("no_reviews"),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 16.dp)
                ) {
                    items(comments) { comment ->
                        val user = userSessionViewModel.users.value.find { it.id == comment.userId }
                        val rating = currentRestaurant.ratings.find { it.userId == comment.userId }

                        Card(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .fillMaxWidth(),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    if (!user?.imageProfile.isNullOrBlank()) {
                                        // CAMBIO MÍNIMO: Detectar tipo de imagen automáticamente
                                        user?.imageProfile?.let { imageProfile ->
                                            when {
                                                ImageConverter.isBase64(imageProfile) -> {
                                                    // Es Base64 - convertir a Bitmap
                                                    val bitmap = ImageConverter.base64ToBitmap(imageProfile)
                                                    if (bitmap != null) {
                                                        Image(
                                                            bitmap = bitmap.asImageBitmap(),
                                                            contentDescription = "Foto de perfil",
                                                            modifier = Modifier
                                                                .size(30.dp)
                                                                .clip(CircleShape),
                                                            contentScale = ContentScale.Crop
                                                        )
                                                    } else {
                                                        // Fallback si falla la conversión
                                                        Icon(
                                                            imageVector = Icons.Outlined.AccountCircle,
                                                            contentDescription = "Usuario",
                                                            tint = MaterialTheme.colorScheme.onSurface,
                                                            modifier = Modifier.size(30.dp)
                                                        )
                                                    }
                                                }
                                                imageProfile.isNotBlank() -> {
                                                    // Es URL - usar AsyncImage como antes
                                                    AsyncImage(
                                                        model = imageProfile,
                                                        contentDescription = "Foto de perfil",
                                                        modifier = Modifier
                                                            .size(30.dp)
                                                            .clip(CircleShape),
                                                        contentScale = ContentScale.Crop
                                                    )
                                                }
                                                else -> {
                                                    // Sin imagen
                                                    Icon(
                                                        imageVector = Icons.Outlined.AccountCircle,
                                                        contentDescription = "Usuario",
                                                        tint = MaterialTheme.colorScheme.onSurface,
                                                        modifier = Modifier.size(30.dp)
                                                    )
                                                }
                                            }
                                        }
                                    } else {
                                        Icon(
                                            imageVector = Icons.Outlined.AccountCircle,
                                            contentDescription = "Usuario",
                                            tint = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }


                                    Spacer(modifier = Modifier.width(8.dp))

                                    val displayName = user?.let { "${it.name} ${it.lastName}" } ?: Translations.t("unknown_user")

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

                                Spacer(modifier = Modifier.width(8.dp))

                                Text(text = comment.comment, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
            }
            if (showDialog && userToBlock != null) {
                ConfirmationDialog(
                    message = Translations.t("block_user_confirm"),
                    onConfirm = {
                        blockedUsersViewModel.blockUser(
                            user = userToBlock!!,
                            onUpdate = { updatedRestaurant ->
                                restaurantViewModel.updateRestaurant(updatedRestaurant)
                                currentRestaurant = updatedRestaurant
                                blockedUsersViewModel.loadBlockedUsers(updatedRestaurant.id)
                            },
                            restaurantId = currentRestaurant.id
                        )
                        Toast.makeText(context, Translations.t("user_blocked_success"), Toast.LENGTH_SHORT).show()
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