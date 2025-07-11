package com.frontend.buhoeats.ui.screens

import android.net.Uri
import android.widget.Toast
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.frontend.buhoeats.R
import com.frontend.buhoeats.models.ContactInfo
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.ConfirmationDialog
import com.frontend.buhoeats.ui.components.FormField
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.ui.components.ValidationMessage
import com.frontend.buhoeats.utils.ValidatorUtils
import com.frontend.buhoeats.viewmodel.RestaurantViewModel
import com.frontend.buhoeats.viewmodel.UserSessionViewModel
import com.frontend.buhoeats.ui.components.DropdownFormField
import com.frontend.buhoeats.ui.components.LocationPickerMap
import com.frontend.buhoeats.ui.theme.AppColors
import com.frontend.buhoeats.ui.theme.ThemeManager

import com.frontend.buhoeats.utils.Translations
import com.frontend.buhoeats.utils.ImageConverter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditLocalScreen(
    isNewLocal: Boolean,
    restaurant: Restaurant? = null,
    navController: NavController,
    onBackClick: () -> Unit = {},
    restaurantViewModel: RestaurantViewModel,
    userSessionViewModel: UserSessionViewModel
) {
    var name by remember { mutableStateOf(restaurant?.name ?: "") }
    var description by remember { mutableStateOf(restaurant?.description ?: "") }
    var adminEmail by remember { mutableStateOf(restaurant?.admin?.let { restaurantViewModel.getUserEmailById(it) } ?: "") }

    var nameError by remember { mutableStateOf(false) }
    var descriptionError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var adminRoleError by remember { mutableStateOf(false) }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var showConfirmationDialog by remember { mutableStateOf(false) }

    var latitud by remember { mutableStateOf(restaurant?.latitud ?: 0.0) }
    var longitud by remember { mutableStateOf(restaurant?.longitud ?: 0.0) }
    var locationError by remember { mutableStateOf(false) }

    val categoryMap = mapOf(
        Translations.t("breakfast") to "Desayuno",
        Translations.t("lunch") to "Almuerzo",
        Translations.t("dinner") to "Cena"
    )
    val categorias = categoryMap.keys.toList()

    var categoriaTraducida by remember { mutableStateOf(
        categoryMap.entries.find { it.value == restaurant?.category }?.key ?: ""
    ) }
    var categoriaError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val allUsers by userSessionViewModel.users

    LaunchedEffect(Unit) {
        userSessionViewModel.loadUsers()
    }

    // CAMBIO MÍNIMO: Agregar variable para almacenar imagen Base64
    var restaurantImageBase64 by remember { mutableStateOf(restaurant?.imageUrl ?: "") }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        // CAMBIO MÍNIMO: Convertir URI a Base64 automáticamente
        uri?.let {
            val base64Image = ImageConverter.uriToBase64(context, it)
            if (base64Image != null) {
                restaurantImageBase64 = base64Image
            }
        }
    }

    Scaffold(
        topBar = { TopBar(showBackIcon = true, onNavClick = onBackClick) },
        bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    nameError = name.isBlank()
                    descriptionError = description.isBlank()
                    emailError = !ValidatorUtils.isValidEmail(adminEmail)
                    categoriaError = categoriaTraducida.isBlank()
                    locationError = latitud == 0.0 && longitud == 0.0

                    if (!nameError && !descriptionError && !emailError && !categoriaError && !locationError) {
                        showConfirmationDialog = true
                    }
                },
                containerColor = Color(0xFF06BB0C),
                contentColor = Color.White,
                modifier = Modifier.size(70.dp),
                shape = CircleShape
            ) {
                Icon(Icons.Default.Check, contentDescription = Translations.t("confirm"), modifier = Modifier.size(45.dp))
            }
        }
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
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isNewLocal) Translations.t("add_location") else Translations.t("edit_location"),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AppColors.texto,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Imagen
                // CAMBIO MÍNIMO: Detectar tipo de imagen automáticamente
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    // Determinar qué imagen mostrar: URI temporal, Base64 o URL original
                    val imageToShow = if (selectedImageUri != null) {
                        restaurantImageBase64 // Mostrar Base64 si se seleccionó nueva imagen
                    } else {
                        restaurant?.imageUrl ?: "" // Mostrar imagen original
                    }

                    when {
                        ImageConverter.isBase64(imageToShow) -> {
                            // Es Base64 - convertir a Bitmap
                            val bitmap = ImageConverter.base64ToBitmap(imageToShow)
                            if (bitmap != null) {
                                androidx.compose.foundation.Image(
                                    bitmap = bitmap.asImageBitmap(),
                                    contentDescription = Translations.t("local_image"),
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                // Fallback
                                DefaultRestaurantImage()
                            }
                        }
                        imageToShow.isNotEmpty() -> {
                            // Es URL - usar AsyncImage como antes
                            AsyncImage(
                                model = imageToShow,
                                contentDescription = Translations.t("local_image"),
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        else -> {
                            // Sin imagen - mostrar default
                            DefaultRestaurantImage()
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

                Spacer(modifier = Modifier.height(24.dp))

                FormField(
                    label = Translations.t("name"),
                    value = name,
                    onValueChange = { name = it; nameError = false },
                    isError = nameError,
                    placeholderText = Translations.t("name_r_placeholder")
                )
                if (nameError) ValidationMessage(Translations.t("name_required"))

                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = Translations.t("description"),
                    value = description,
                    onValueChange = { description = it; descriptionError = false },
                    isError = descriptionError,
                    isMultiline = true,
                    placeholderText = Translations.t("description_placeholder")
                )
                if (descriptionError) ValidationMessage(Translations.t("description_required"))

                Spacer(modifier = Modifier.height(16.dp))

                DropdownFormField(
                    label = Translations.t("category"),
                    options = categorias,
                    selectedOption = categoriaTraducida,
                    onOptionSelected = {
                        categoriaTraducida = it
                        categoriaError = false
                    },
                    isError = categoriaError,
                )
                if (categoriaError) ValidationMessage(Translations.t("select_category"))

                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = Translations.t("admin_email"),
                    value = adminEmail,
                    onValueChange = { adminEmail = it; emailError = false },
                    isError = emailError,
                    placeholderText = Translations.t("admin_placeholder")
                )
                if (emailError) ValidationMessage(Translations.t("invalid_email"))
                if (adminRoleError) ValidationMessage(Translations.t("invalid_admin_role"))

                Spacer(modifier = Modifier.height(16.dp))

                LocationPickerMap(
                    lat = latitud,
                    lon = longitud,
                    onLocationChange = { lat, lon ->
                        latitud = lat
                        longitud = lon
                        locationError = false
                    }
                )
                if (locationError) ValidationMessage(Translations.t("invalid_location"))
            }

            if (showConfirmationDialog) {
                ConfirmationDialog(
                    message = if (isNewLocal)
                        Translations.t("confirm_add_local")
                    else
                        Translations.t("confirm_edit_local"),
                    onConfirm = {
                        val adminUser = allUsers.find {
                            it.email.trim().equals(adminEmail.trim(), ignoreCase = true) && it.rol == "admin"
                        }
                        if (adminUser != null) {
                            val categoria = categoryMap[categoriaTraducida] ?: ""
                            // CAMBIO MÍNIMO: Usar Base64 en lugar de URI para el backend
                            val updatedRestaurant = Restaurant(
                                id = restaurant?.id ?: restaurantViewModel.getNextRestaurantId().toString(),
                                name = name,
                                description = description,
                                imageUrl = if (selectedImageUri != null) {
                                    restaurantImageBase64 // Usar Base64 si se seleccionó nueva imagen
                                } else {
                                    restaurant?.imageUrl ?: "https://images.unsplash.com/photo-1525610553991-2bede1a236e2"
                                },
                                category = categoria,
                                contactInfo = restaurant?.contactInfo ?: ContactInfo("", "", "", ""),
                                ratings = restaurant?.ratings?.toMutableList() ?: mutableListOf(),
                                comments = restaurant?.comments?.toMutableList() ?: mutableListOf(),
                                menu = restaurant?.menu ?: emptyList(),
                                promos = restaurant?.promos ?: emptyList(),
                                latitud = latitud,
                                longitud = longitud,
                                admin = adminUser.id,
                                blockedUsers = restaurant?.blockedUsers ?: emptyList()
                            )

                            if (isNewLocal) {
                                restaurantViewModel.addRestaurant(updatedRestaurant)
                                Toast.makeText(context, Translations.t("location_created"), Toast.LENGTH_SHORT).show()
                            } else {
                                restaurantViewModel.updateRestaurant(updatedRestaurant)
                            }

                            showConfirmationDialog = false
                            navController.popBackStack()
                        } else {
                            emailError = true
                            adminRoleError = true
                            showConfirmationDialog = false
                            return@ConfirmationDialog
                        }
                    },
                    onDismiss = {
                        showConfirmationDialog = false
                    }
                )
            }
        }
    }
}

@Composable
private fun DefaultRestaurantImage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.accent)
    )
}
