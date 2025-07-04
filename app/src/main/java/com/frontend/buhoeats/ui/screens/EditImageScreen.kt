package com.frontend.buhoeats.ui.screens

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.viewmodel.UserSessionViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.frontend.buhoeats.R
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import com.frontend.buhoeats.models.User
import android.widget.Toast
import com.frontend.buhoeats.ui.theme.AppColors
import com.frontend.buhoeats.ui.theme.ThemeManager
import com.frontend.buhoeats.utils.Translations
import com.frontend.buhoeats.viewmodel.RestaurantViewModel
import com.frontend.buhoeats.utils.ImageConverter
import androidx.compose.ui.graphics.asImageBitmap


fun isAdminOfRestaurant(user: User?, restaurant: Restaurant): Boolean {
    return user?.rol == "admin" && restaurant.admin == user.id
}
@Composable
fun EditImageScreen(
    navController: NavController,
    restaurant: Restaurant,
    userSessionViewModel: UserSessionViewModel,
    restaurantViewModel: RestaurantViewModel
) {
    val currentUser = userSessionViewModel.currentUser.value
    val isAdmin = isAdminOfRestaurant(currentUser, restaurant)

    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val currentImageUrl = restaurant.imageUrl

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    if (!isAdmin) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
        return
    }

    Scaffold(
        topBar = { TopBar(showBackIcon = true, onNavClick = { navController.popBackStack() }) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            val backgroundImage = if (ThemeManager.isDarkTheme)
                painterResource(id = R.drawable.backgrounddark)
            else
                painterResource(id = R.drawable.backgroundlighttheme)

            Image(
                painter = backgroundImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    Translations.t("edit_restaurant_image"),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.texto
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    when {
                        selectedImageUri != null -> {
                            // CAMBIO: Detectar tipo de imagen automáticamente para imagen seleccionada
                            val imageToShow = selectedImageUri?.toString() ?: ""
                            when {
                                ImageConverter.isBase64(imageToShow) -> {
                                    // Es Base64 - convertir a Bitmap
                                    val bitmap = ImageConverter.base64ToBitmap(imageToShow)
                                    if (bitmap != null) {
                                        Image(
                                            bitmap = bitmap.asImageBitmap(),
                                            contentDescription = Translations.t("selected_image"),
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        // Fallback si falla la conversión
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text("Error loading image")
                                        }
                                    }
                                }
                                imageToShow.isNotBlank() -> {
                                    // Es URI/URL - usar AsyncImage como antes
                                    AsyncImage(
                                        model = selectedImageUri,
                                        contentDescription = Translations.t("selected_image"),
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                else -> {
                                    // Sin imagen
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("No image")
                                    }
                                }
                            }
                        }

                        currentImageUrl.isNotBlank() -> {
                            // CAMBIO: Detectar tipo de imagen automáticamente para imagen actual
                            when {
                                ImageConverter.isBase64(currentImageUrl) -> {
                                    // Es Base64 - convertir a Bitmap
                                    val bitmap = ImageConverter.base64ToBitmap(currentImageUrl)
                                    if (bitmap != null) {
                                        Image(
                                            bitmap = bitmap.asImageBitmap(),
                                            contentDescription = Translations.t("current_image"),
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        // Fallback si falla la conversión
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text("Error loading image")
                                        }
                                    }
                                }
                                currentImageUrl.isNotBlank() -> {
                                    // Es URL - usar AsyncImage como antes
                                    AsyncImage(
                                        model = currentImageUrl,
                                        contentDescription = Translations.t("current_image"),
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                                else -> {
                                    // Sin imagen
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("No image")
                                    }
                                }
                            }
                        }
                    }

                    IconButton(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        modifier = Modifier
                            .offset(x = (-8).dp, y = (-8).dp)
                            .size(48.dp)
                            .background(Color.White, CircleShape)
                            .border(1.dp, Color.Gray, CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = Translations.t("change_image")
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        // CAMBIO: Convertir imagen URI a Base64 si se seleccionó una nueva
                        val nuevaImagen = selectedImageUri?.let { uri ->
                            ImageConverter.uriToBase64(context, uri)
                        } ?: currentImageUrl

                        restaurantViewModel.updateRestaurantImage(restaurant.id, nuevaImagen)
                        Toast.makeText(context, Translations.t("image_saved_successfully"), Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    },
                    enabled = selectedImageUri != null || currentImageUrl.isNotBlank(),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth(0.6f)
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF06BB0C),
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text(
                        Translations.t("save_changes"),
                        fontSize = 18.sp
                    )
                }
            }
        }
    }
}
