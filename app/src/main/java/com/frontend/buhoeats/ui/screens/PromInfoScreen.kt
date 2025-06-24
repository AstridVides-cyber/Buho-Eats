package com.frontend.buhoeats.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.frontend.buhoeats.R
import com.frontend.buhoeats.models.ContactInfo
import com.frontend.buhoeats.models.Promo
import com.frontend.buhoeats.navigation.Screens
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.ContactCard
import com.frontend.buhoeats.ui.components.EditFloatingButton
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.ui.components.ValidationMessage
import com.frontend.buhoeats.viewmodel.PromoViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import com.frontend.buhoeats.data.InMemoryUserDataSource
import com.frontend.buhoeats.utils.ValidatorUtils.isOnlyNumbers


import com.frontend.buhoeats.viewmodel.UserSessionViewModel


@OptIn(ExperimentalComposeUiApi::class)
@RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun PromoInfoScreen(
        isAdmin: Boolean = false,
        promo: Promo,
        restaurantName: String,
        contactInfo: ContactInfo,
        navController: NavController,
        promoViewModel: PromoViewModel = viewModel(),
        onBackClick: () -> Unit = {},
        userSessionViewModel: UserSessionViewModel
    ) {
        val esNuevaPromo = promo.name.isBlank() && promo.description.isBlank()
        var isEditing by remember { mutableStateOf(esNuevaPromo) }

        var name by remember { mutableStateOf(promo.name) }
        var description by remember { mutableStateOf(promo.description) }
        var promprice by remember { mutableStateOf(promo.promprice) }
        var price by remember { mutableStateOf(promo.price) }
        var reglas by remember { mutableStateOf(promo.reglas) }

        var showError by remember { mutableStateOf(false) }
        var promPriceError by remember { mutableStateOf(false) }
        var currentPriceError by remember { mutableStateOf(false) }
        val currentUser = userSessionViewModel.currentUser.value
        val adminRestaurant = InMemoryUserDataSource.getRestaurants().firstOrNull { it.admin == currentUser?.id.toString() }
        val context = LocalContext.current


    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            selectedImageUri = uri
        }



        Scaffold(
            topBar = {
                TopBar(
                    showBackIcon = true,
                    onNavClick = onBackClick
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            },
            floatingActionButton = {
                if (isAdmin && !isEditing && !esNuevaPromo) {
                    EditFloatingButton(onClick = { isEditing = true })
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
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
                        .padding(16.dp)
                ) {
                    Text("Promoción", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = Color.Black)

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(restaurantName, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .border(2.dp, Color.Gray, RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        when {
                            selectedImageUri != null -> {
                                AsyncImage(
                                    model = selectedImageUri,
                                    contentDescription = "Imagen seleccionada",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            promo.imageUrl.isNotBlank() -> {
                                AsyncImage(
                                    model = promo.imageUrl,
                                    contentDescription = "Imagen actual",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            else -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.LightGray)
                                )
                            }
                        }

                        if (isEditing) {
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
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                if (isEditing) {
                    Text("Titulo:", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                        focusedContainerColor = Color.White.copy(alpha = 0.95f)))
                } else {
                    Text(name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (isEditing) {
                        Column(Modifier.weight(1f)) {
                            Text("Antes:", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                            OutlinedTextField(
                                value = promprice,
                                onValueChange = {
                                    promprice = it
                                    promPriceError = !isOnlyNumbers(it)
                                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                                    focusedContainerColor = Color.White.copy(alpha = 0.95f),
                                    errorContainerColor = Color.White.copy(alpha = 0.8f)
                                ),
                                isError = promPriceError,
                            )
                            if (promPriceError) {
                                ValidationMessage("Solo números permitidos")
                            }
                        }
                        Column(Modifier.weight(1f)) {
                            Text("Ahora:", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                            OutlinedTextField(value = price,
                                onValueChange = {
                                    price = it
                                    currentPriceError = !isOnlyNumbers(it)
                                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                                    focusedContainerColor = Color.White.copy(alpha = 0.95f),
                                    errorContainerColor = Color.White.copy(alpha = 0.8f)
                                ),
                                isError = currentPriceError,
                            )
                            if (currentPriceError) {
                                ValidationMessage("Solo números permitidos")
                            }
                        }
                    } else {
                        Text("$${promprice}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Green)
                        Text("$${price}", fontSize = 20.sp, color = Color.Gray, textDecoration = TextDecoration.LineThrough)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isEditing) {
                    Text("Descripción:", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                    OutlinedTextField(value = description,
                        onValueChange = { description = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                            focusedContainerColor = Color.White.copy(alpha = 0.95f)
                        ))
                } else {
                    Text(description, fontSize = 16.sp, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Reglas:", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))

                if (isEditing) {
                    OutlinedTextField(value = reglas,
                        onValueChange = { reglas = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                            focusedContainerColor = Color.White.copy(alpha = 0.95f)
                        ))
                } else {
                    Text(text = if (reglas.isNotBlank()) reglas else "Sin reglas específicas", fontSize = 15.sp, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(12.dp))
                ContactCard(contactInfo)

                if (showError) {
                    Spacer(modifier = Modifier.height(8.dp))
                    ValidationMessage("Por favor completa todos los campos")
                }
                if ((promPriceError || currentPriceError) && !showError) {
                    Spacer(modifier = Modifier.height(8.dp))
                    ValidationMessage("Los precios solo deben contener números")
                }

                if (isEditing) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            shape = RoundedCornerShape(12.dp),
                            onClick = {
                                navController.navigate(Screens.Promocion.route)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFC11D0C),
                                contentColor = Color.White
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                            modifier = Modifier
                                .weight(0.8f)
                                .height(50.dp)
                                .padding(end = 10.dp)
                        ) {
                            Text("Cancelar", fontSize = 18.sp)
                        }

                        Button(
                            shape = RoundedCornerShape(12.dp),
                            onClick = {
                            if (name.isBlank() || description.isBlank() || promprice.isBlank() || price.isBlank() || promPriceError || currentPriceError) {
                                showError = true
                            } else {
                                val formattedPromPrice = "%.2f".format(promprice.toDoubleOrNull() ?: 0.0)
                                val formattedPrice = "%.2f".format(price.toDoubleOrNull() ?: 0.0)

                                val nuevaPromo = Promo(
                                    id = promo.id,
                                    name = name,
                                    description = description,
                                    promprice = formattedPromPrice,
                                    price = formattedPrice,
                                    imageUrl = selectedImageUri?.toString()
                                        ?: promo.imageUrl.ifBlank {
                                            "https://images.unsplash.com/photo-1722639096462-dc586c185186"
                                        },
                                    reglas = reglas,
                                    restaurantId = adminRestaurant?.id?.toString() ?: promo.restaurantId
                                )

                                if (esNuevaPromo) {
                                    promoViewModel.addPromo(nuevaPromo, currentUser)
                                } else {
                                    promoViewModel.updatePromo(nuevaPromo, currentUser)
                                }
                                val message = if (esNuevaPromo) "Promoción creada exitosamente" else "Promoción guardada exitosamente"
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

                                showError = false
                                isEditing = false
                                navController.navigate(Screens.Promocion.route)
                            }
                        },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF06BB0C),
                                contentColor = Color.White
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                            modifier = Modifier
                                .weight(0.8f)
                                .height(50.dp)
                                .padding(start = 10.dp)
                        ) {
                            Text("Guardar", fontSize = 18.sp)
                        }

                    }

                }
            }
        }
    }
}