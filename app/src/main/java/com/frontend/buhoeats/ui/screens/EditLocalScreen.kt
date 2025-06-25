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

    val categorias = listOf("Desayuno", "Almuerzo", "Cena")
    var categoria by remember { mutableStateOf(restaurant?.category ?: "") }
    var categoriaError by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val allUsers by userSessionViewModel.users

    LaunchedEffect(Unit) {
        userSessionViewModel.loadUsers()
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Scaffold(
        topBar = {
            TopBar(showBackIcon = true, onNavClick = onBackClick)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    nameError = name.isBlank()
                    descriptionError = description.isBlank()
                    emailError = !ValidatorUtils.isValidEmail(adminEmail)
                    categoriaError = categoria.isBlank()
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
                Icon(Icons.Default.Check, contentDescription = "Confirmar", modifier = Modifier.size(45.dp))
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
                    text = if (isNewLocal) "Agregar local" else "Editar local",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = AppColors.texto,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Imagen del local
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    val imageToShow = selectedImageUri?.toString() ?: restaurant?.imageUrl

                    if (!imageToShow.isNullOrEmpty()) {
                        AsyncImage(
                            model = imageToShow,
                            contentDescription = "Imagen del local",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(AppColors.accent)
                        )
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
                            contentDescription = "Cambiar imagen"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                FormField(
                    label = "Nombre:",
                    value = name,
                    onValueChange = { name = it; nameError = false },
                    isError = nameError,
                    placeholderText = "Ej. Pupusería La Esperanza"
                )
                if (nameError) ValidationMessage("El nombre no puede estar vacío")

                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = "Descripción:",
                    value = description,
                    onValueChange = { description = it; descriptionError = false },
                    isError = descriptionError,
                    isMultiline = true,
                    placeholderText = "Breve descripción del local"
                )
                if (descriptionError) ValidationMessage("La descripción no puede estar vacía")


                Spacer(modifier = Modifier.height(16.dp))

                DropdownFormField(
                    label = "Categoría:",
                    options = categorias,
                    selectedOption = categoria,
                    onOptionSelected = {
                        categoria = it
                        categoriaError = false
                    },
                    isError = categoriaError
                )
                if (categoriaError) ValidationMessage("Selecciona una categoría")

                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = "Administrador:",
                    value = adminEmail,
                    onValueChange = { adminEmail = it; emailError = false },
                    isError = emailError,
                    placeholderText = "Correo del administrador"
                )
                if (emailError) ValidationMessage("Correo no válido")
                if (adminRoleError) ValidationMessage("El correo debe pertenecer a un administrador")


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
                if (locationError) ValidationMessage("Selecciona una ubicación válida")


            }
            if (showConfirmationDialog) {
                ConfirmationDialog(
                    message = if (isNewLocal) "¿Estás seguro que deseas agregar un nuevo local?" else "¿Deseas guardar los cambios en el local?",
                    onConfirm = {
                        val adminUser = allUsers.find {
                            it.email.trim().equals(adminEmail.trim(), ignoreCase = true) && it.rol == "admin"
                        }
                        if (adminUser != null) {
                            val updatedRestaurant = Restaurant(
                                id = restaurant?.id ?: restaurantViewModel.getNextRestaurantId().toString(),
                                name = name,
                                description = description,
                                imageUrl = selectedImageUri?.toString()
                                    ?: restaurant?.imageUrl
                                    ?: "https://images.unsplash.com/photo-1525610553991-2bede1a236e2",
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
                                Toast.makeText(context, "Local creado exitosamente", Toast.LENGTH_SHORT).show()
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
