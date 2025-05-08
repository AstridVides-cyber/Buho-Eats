package com.frontend.buhoeats.ui.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.frontend.buhoeats.ui.components.RatingBar
import androidx.compose.foundation.rememberScrollState
import com.frontend.buhoeats.data.DummyData
import coil.compose.AsyncImage
import com.frontend.buhoeats.data.Restaurant
import com.frontend.buhoeats.ui.components.ContactCard
import com.frontend.buhoeats.ui.components.DishCard
import com.frontend.buhoeats.ui.components.Opinion

@Composable
fun RestauranteScreen() {

    val restaurant = DummyData.getRestaurants()[0]

    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomNavigationBar()
        }
    ) { paddingValues ->
        RestauranteContent(
            modifier = Modifier.padding(paddingValues),
            restaurant = restaurant
        )
    }
}


@Composable
fun RestauranteContent(restaurant: Restaurant, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(restaurant.name, fontSize = 22.sp, fontWeight = FontWeight.Bold)

        AsyncImage(
            model = restaurant.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        ContactCard(contactInfo = restaurant.contactInfo)

        Spacer(modifier = Modifier.padding(10.dp))

        Text("Menú del día", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))

        restaurant.menu.forEach { dish ->
            DishCard(dish = dish)
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Text("Califica la app", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        RatingBar(rating = 4)

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("Escribe tu opinión") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = { /* publicar */ }, modifier = Modifier.align(Alignment.End)) {
            Text("Publicar")
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Text("Calificaciones y opiniones", fontWeight = FontWeight.Medium)

        restaurant.reviews.forEach { review ->
            Opinion(review)
        }
    }
}


