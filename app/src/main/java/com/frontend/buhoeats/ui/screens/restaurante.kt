package com.frontend.buhoeats.ui.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.frontend.buhoeats.ui.components.RatingBar
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import com.frontend.buhoeats.data.DummyData
import coil.compose.AsyncImage
import com.frontend.buhoeats.models.ContactInfo
import com.frontend.buhoeats.models.Dish
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.models.Review


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

        Text("Menú del día", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
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

@Composable
fun ContactCard(contactInfo: ContactInfo) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF49726D),
            contentColor = Color.White
        ),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text("Correo: ${contactInfo.email}")
            Text("Teléfono: ${contactInfo.phone}")
            Text("Horario: ${contactInfo.hours}")
            Text("Dirección: ${contactInfo.address}")
        }
    }
}

@Composable
fun DishCard(dish: Dish) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = dish.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(dish.name, fontWeight = FontWeight.Bold)
                Text(dish.description)
            }
            Text(dish.price, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun Opinion(review: Review) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(Icons.Filled.Person, contentDescription = "Persona")
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(review.username, fontWeight = FontWeight.Bold)
            Text(review.comment)
            RatingBar(rating = review.rating)
        }
    }
}

@Composable
fun RatingBar(rating: Int) {
    Row {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Estrella",
                tint = if (index < rating) Color(0xFFFFD700) else Color.LightGray
            )
        }
    }
}
