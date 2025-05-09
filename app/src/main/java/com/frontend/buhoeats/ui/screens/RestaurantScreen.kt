package com.frontend.buhoeats.ui.screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.frontend.buhoeats.data.DummyData
import coil.compose.AsyncImage
import com.frontend.buhoeats.R
import com.frontend.buhoeats.data.Restaurant
import com.frontend.buhoeats.ui.components.ContactCard
import com.frontend.buhoeats.ui.components.DishCard
import com.frontend.buhoeats.ui.components.Opinion

@Composable

fun RestauranteScreen() {
    val restaurant = DummyData.getRestaurants()[0]

    Scaffold(
        topBar = {
            TopBar(
                showBackIcon = true
            )
        },
        bottomBar = {
            BottomNavigationBar()
        }
    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

            Image(
                painter = painterResource(id = R.drawable.backgroundlighttheme),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            RestauranteContent(
                modifier = Modifier.fillMaxSize(),
                restaurant = restaurant
            )
        }
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
        Text(restaurant.name, fontSize = 22.sp, fontWeight = FontWeight.Bold , modifier = Modifier.padding(bottom = 10.dp))

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

            RatingBar(rating = 4)
        }


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
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Calificaciones y opiniones", fontWeight = FontWeight.Medium)

            restaurant.reviews.forEach { review ->
                Opinion(review)
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMenu(){
    RestauranteScreen()
}

