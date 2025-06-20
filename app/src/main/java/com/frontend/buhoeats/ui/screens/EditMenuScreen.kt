package com.frontend.buhoeats.ui.screens

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.frontend.buhoeats.R
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.models.Dish
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.FormField
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.ui.components.ValidationMessage
import com.frontend.buhoeats.viewmodel.UserSessionViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditMenuScreen(
    navController: NavController,
    userSessionViewModel: UserSessionViewModel
) {
    val currentUser = userSessionViewModel.currentUser.value
    val restaurant = DummyData.getRestaurants().find { it.admin == currentUser?.id }

    if (currentUser?.rol != "admin" || restaurant == null) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
        return
    }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> if (uri != null) imageUri = uri }

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var descriptionError by remember { mutableStateOf(false) }
    var priceError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopBar(showBackIcon = true) { navController.popBackStack() } },
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.backgroundlighttheme),
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
                Text("Editar Menú del día:", fontWeight = FontWeight.Bold, fontSize = 24.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFB8C1EC))
                        .clickable { imagePickerLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    when (imageUri) {
                        null -> {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Agregar imagen",
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                        else -> {
                            AsyncImage(
                                model = imageUri,
                                contentDescription = "Imagen seleccionada",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = "Nombre del plato:",
                    value = name,
                    onValueChange = { name = it; nameError = false },
                    isError = nameError,
                    placeholderText = "Ej. Pupusas revueltas"
                )
                if (nameError) ValidationMessage("El nombre no puede estar vacío")

                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = "Descripción:",
                    value = description,
                    onValueChange = { description = it; descriptionError = false },
                    isError = descriptionError,
                    isMultiline = true,
                    placeholderText = "Breve descripción del plato"
                )
                if (descriptionError) ValidationMessage("La descripción no puede estar vacía")

                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = "Precio:",
                    value = price,
                    onValueChange = { price = it; priceError = false },
                    isError = priceError,
                    placeholderText = "Ej. 2.50"
                )
                if (priceError) ValidationMessage("El precio no puede estar vacío")

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { navController.popBackStack() },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC11D0C)),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .height(50.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Text("Cancelar", color = Color.White, fontSize = 16.sp)
                    }

                    Button(
                        onClick = {
                            nameError = name.isBlank()
                            descriptionError = description.isBlank()
                            priceError = price.isBlank()

                            if (!nameError && !descriptionError && !priceError) {
                                val newDish = Dish(
                                    id = restaurant.menu.size + 1,
                                    name = name,
                                    description = description,
                                    imageUrl = imageUri?.toString() ?: "https://plus.unsplash.com/premium_photo-1670604211960-82b8d84f6aea",
                                    price = "$$price"
                                )
                                val updatedRestaurant = restaurant.copy(
                                    menu = restaurant.menu + newDish
                                )
                                DummyData.updateRestaurant(updatedRestaurant)
                                navController.popBackStack()
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF06BB0C)),
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                            .height(50.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Text("Confirmar", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
