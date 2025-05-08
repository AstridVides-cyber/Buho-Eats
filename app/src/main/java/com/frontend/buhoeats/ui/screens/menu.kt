package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frontend.buhoeats.R
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.ui.components.RestaurantCard
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar


@Composable
fun MenuScreen() {
    val restaurantList = DummyData.getRestaurants()
    var selectedFilter by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomNavigationBar()
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.backgroundlighttheme),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item {
                    GreetingSection()
                    Spacer(modifier = Modifier.height(16.dp))
                    FilterSection(
                        onFilterSelected = { selectedFilter = it },
                        onReset = { selectedFilter = null },
                        resultCount = restaurantList.size
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Restaurantes",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(restaurantList) { restaurant ->
                    RestaurantCard(restaurant = restaurant)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}



@Composable
fun GreetingSection() {
    Column {
        Text(
            text = "Buenas tardes, Usuario",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            // Aquí se debe ingresar las imagenes
            /*Image(
                painter = painterResource(id = R.drawable.example_image),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )*/
        }
    }
}

@Composable
fun FilterSection(
    onFilterSelected: (String) -> Unit,
    onReset: () -> Unit,
    resultCount: Int
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            val buttonColors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF588B8B)
            )

            Button(onClick = { onFilterSelected("Desayuno") }, colors = buttonColors) {
                Text("Desayuno", color = Color.White)
            }
            Button(onClick = { onFilterSelected("Almuerzo") }, colors = buttonColors) {
                Text("Almuerzo", color = Color.White)
            }
            Button(onClick = { onFilterSelected("Cena") }, colors = buttonColors) {
                Text("Cena", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Resultados: $resultCount", fontSize = 16.sp)
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = { onReset() }) {
                Text("Restablecer")
            }
        }
    }
}



