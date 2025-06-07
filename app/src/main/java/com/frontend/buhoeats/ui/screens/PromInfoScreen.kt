package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.material3.SearchBarDefaults.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.frontend.buhoeats.R
import com.frontend.buhoeats.models.ContactInfo
import com.frontend.buhoeats.models.Promo
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.ContactCard
import com.frontend.buhoeats.ui.components.EditFloatingButton
import com.frontend.buhoeats.ui.components.TopBar

@Composable
fun PromoInfoScreen(
    isAdmin: Boolean = false,
    promo: Promo,
    restaurantName: String,
    contactInfo: ContactInfo,
    navController: NavController,
    onBackClick: () -> Unit = {}
) {
    var isEditing by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf(promo.name) }
    var description by remember { mutableStateOf(promo.description) }
    var promprice by remember { mutableStateOf(promo.promprice) }
    var price by remember { mutableStateOf(promo.price) }
    var reglas by remember { mutableStateOf(promo.reglas ?: "") }

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
            if (isAdmin && !isEditing) {
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

                AsyncImage(
                    model = promo.imageUrl,
                    contentDescription = "Imagen de promoción",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (isEditing) {
                    Text("Titulo:", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                    OutlinedTextField(value = name, onValueChange = { name = it }, modifier = Modifier.fillMaxWidth())
                } else {
                    Text(name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (isEditing) {

                        Text("Ahora:", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                        OutlinedTextField(value = price, onValueChange = { price = it }, modifier = Modifier.weight(1f))
                        Text("Antes:", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                        OutlinedTextField(value = promprice, onValueChange = { promprice = it },  modifier = Modifier.weight(1f))

                    } else {
                        Text(promprice, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Green)
                        Text(price, fontSize = 20.sp, color = Color.Gray, textDecoration = TextDecoration.LineThrough)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


                if (isEditing) {
                    Text("Descripción:", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)

                    OutlinedTextField(value = description, onValueChange = { description = it },  modifier = Modifier.fillMaxWidth())
                } else {
                    Text(description, fontSize = 16.sp, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Reglas:", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))

                if (isEditing) {
                    OutlinedTextField(value = reglas, onValueChange = { reglas = it }, modifier = Modifier.fillMaxWidth())
                } else {
                    Text(text = if (reglas.isNotBlank()) reglas else "Sin reglas específicas", fontSize = 15.sp, color = Color.Black)
                }

                Spacer(modifier = Modifier.height(16.dp))
                ContactCard(contactInfo)

                if (isEditing) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(onClick = {
                            isEditing = false
                        },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF3D405B),
                                contentColor = Color.White
                            )
                        )

                        {
                            Text("Guardar")
                        }
                        OutlinedButton(onClick = {

                            name = promo.name
                            description = promo.description
                            promprice = promo.promprice
                            price = promo.price
                            reglas = promo.reglas ?: ""
                            isEditing = false
                        }) {
                            Text("Cancelar")
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PromoInfoScreenPreview() {
    val promo = Promo(
        id = 1,
        name = "Promo Sushi",
        description = "¡Disfruta 2x1 en rollos de sushi!",
        imageUrl = "https://images.unsplash.com/photo-1553621042-f6e147245754",
        price = "$10.00",
        promprice = "$5.00",
        reglas = "Solo aplica los viernes"
    )

    val contactInfo = ContactInfo(
        email = "contacto@sushiplace.com",
        phone = "2222-3333",
        hours = "10:00 AM - 10:00 PM",
        address = "Avenida Los Sushi #123"
    )
    PromoInfoScreen(
        promo = promo,
        restaurantName = "Sushi Place",
        contactInfo = contactInfo,
        navController = rememberNavController()
    )
}
