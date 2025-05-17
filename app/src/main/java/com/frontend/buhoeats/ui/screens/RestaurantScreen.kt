package com.frontend.buhoeats.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
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
import com.frontend.buhoeats.ui.components.Opinion
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.frontend.buhoeats.R
import com.frontend.buhoeats.ui.components.ContactCard
import com.frontend.buhoeats.ui.components.DishCard
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.frontend.buhoeats.models.ContactInfo
import com.frontend.buhoeats.models.Dish
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.models.Review

@Composable
fun RestaurantScreen(
    navController: NavController,
    restaurant: Restaurant
) {
    Scaffold(
        topBar = {
            TopBar(
                showBackIcon = true,
                onNavClick = { navController.popBackStack() }

            )
        },
        bottomBar = { BottomNavigationBar() }
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

            RestaurantContent(
                modifier = Modifier.fillMaxSize(),
                restaurant = restaurant
            )
        }
    }
}

@Composable
fun RestaurantContent(restaurant: Restaurant, modifier: Modifier = Modifier) {

    var isFavorite by rememberSaveable { mutableStateOf(false) }
    var rating by rememberSaveable { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }
    val newReviews = remember { mutableStateListOf<Review>() }
    var reviews by remember { mutableStateOf(restaurant.reviews) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = restaurant.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            IconButton(onClick = { isFavorite = !isFavorite }) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
                    tint = if (isFavorite) Color.Red else Color.Gray,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            AsyncImage(
                model = restaurant.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp)
                    )
            )
        }

        ContactCard(contactInfo = restaurant.contactInfo)

        Spacer(modifier = Modifier.padding(10.dp))

        Text("Menú del día", fontSize = 20.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(8.dp))

        restaurant.menu.forEach { dish ->
            DishCard(dish = dish)
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Califica la app",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Log.d("Rating", "Valor del rating: $rating")

            RatingBar(rating = rating, onRatingChanged = { rating = it })
            Spacer(modifier = Modifier.height(8.dp))

        }

        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Escribe tu opinión") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (comment.isNotBlank() && rating > 0) {
                    newReviews.add(Review(username = "Usuario", comment = comment, rating = rating))
                    comment = ""
                    rating = 0
                }
            },
            modifier = Modifier.align(Alignment.End)
                .padding(5.dp)
        ) {
            Text("Publicar")
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))
        Spacer(modifier = Modifier.size(10.dp))
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Calificaciones y opiniones", fontWeight = FontWeight.Medium,fontSize = 18.sp
            )
            Spacer(modifier = Modifier.size(15.dp))

            (reviews + newReviews).forEach { review ->
                Opinion(review)
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun RestaurantScreenPreview() {
    val sampleRestaurant =  Restaurant(
        id = 1,
        name = "Restaurante El Buen Sabor",
        description = "Comida típica salvadoreña con un toque moderno.",
        imageUrl = "https://images.unsplash.com/photo-1600891964599-f61ba0e24092",
        categories = listOf("Salvadoreña", "Típica", "Familiar"),
        contactInfo = ContactInfo(
            email = "contacto@elbuen.sv",
            phone = "2222-3333",
            hours = "Lunes a Domingo, 8:00 AM - 9:00 PM",
            address = "Calle El Progreso #123, San Salvador"
        ),
        reviews = listOf(
            Review(
                username = "Juan Pérez",
                comment = "Excelente atención y la comida deliciosa.",
                rating = 5
            ),
            Review(
                username = "Ana López",
                comment = "Muy bonito el lugar y buen ambiente.",
                rating = 4
            )
        ),
        menu = listOf(
            Dish(
                id = 1,
                name = "Pupusas revueltas",
                description = "Pupusas con chicharrón, frijoles y queso.",
                imageUrl = "https://images.unsplash.com/photo-1625948137139-d6672c58db6e",
                price = "$5.00"
            ),
            Dish(
                id = 2,
                name = "Sopa de gallina india",
                description = "Sopa tradicional con vegetales frescos.",
                imageUrl = "https://images.unsplash.com/photo-1605478371437-045aa03f2dc1",
                price = "$5.00"
            )
        )
    )
    RestaurantScreen(
        navController = rememberNavController(),
        restaurant = sampleRestaurant
    )
}