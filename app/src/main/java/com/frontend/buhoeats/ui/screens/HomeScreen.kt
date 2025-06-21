package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.navigation.Screens
import com.frontend.buhoeats.ui.components.RestaurantCard
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.ConfirmationDialog
import com.frontend.buhoeats.ui.components.DeleteButton
import com.frontend.buhoeats.ui.components.DeleteFloatingButton
import com.frontend.buhoeats.ui.components.EditFloatingButton
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.viewmodel.RestaurantViewModel
import com.frontend.buhoeats.viewmodel.UserSessionViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    onRestaurantClick: (Int) -> Unit,
    navController: NavController,
    userSessionViewModel: UserSessionViewModel,
    restaurantViewModel: RestaurantViewModel
) {
    val restaurantList = restaurantViewModel.restaurantList
    var selectedFilter by remember { mutableStateOf<String?>(null) }
    val currentUser by userSessionViewModel.currentUser

    val isAdmin = currentUser?.rol == "admin"
    val isSuperAdmin = currentUser?.rol == "superadmin"
    val myRestaurant = restaurantList.find { it.admin == currentUser?.id }
    var isEditing by remember { mutableStateOf(false) }
    var isCreatingNewLocal by remember { mutableStateOf(false) }
    var isDeleting by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var restaurantToDelete by remember { mutableStateOf<Restaurant?>(null) }




    val filteredRestaurants = if (isAdmin) {
        restaurantList.filter { it.admin != currentUser?.id }
    } else {
        restaurantList
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val adminRestaurant = DummyData.getRestaurants().find { it.admin == currentUser?.id }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (currentUser != null) {
                Scaffold(
                    topBar = { TopBar(showBackIcon = true) { scope.launch { drawerState.close() } } },
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        SettingSlider(
                            navController = navController,
                            currentUser = currentUser!!,
                            restaurant = adminRestaurant,
                            onNavigateToProfile = { navController.navigate(Screens.Profile.route) })
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    showMenuIcon = true,
                    onNavClick = {
                        scope.launch { drawerState.open() }
                    }
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            },
            floatingActionButton = {
                if (isSuperAdmin) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.End
                    ) {

                        if (!isDeleting) {
                            EditFloatingButton(onClick = { isEditing = !isEditing })
                        }
                        if (!isEditing) {
                            DeleteFloatingButton(onClick = { isDeleting = !isDeleting })
                        }                    }
                }
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
                if (showDialog && restaurantToDelete != null) {
                    ConfirmationDialog(
                        message = "¿Seguro que deseas eliminar el local?",
                        onConfirm = {
                            restaurantViewModel.deleteRestaurant(restaurantToDelete!!.id)
                            showDialog = false
                            isDeleting = false
                        },
                        onDismiss = {
                            showDialog = false
                            restaurantToDelete = null
                        }
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    item {
                        GreetingSection(userName = currentUser?.name ?: "Usuario")
                        Spacer(modifier = Modifier.height(16.dp))
                        FilterSection(
                            onFilterSelected = { selectedFilter = it },
                            onReset = { selectedFilter = null },
                            resultCount = restaurantList.size
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        if (isAdmin && myRestaurant != null) {
                            Text(
                                text = "Su local:",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            RestaurantCard(restaurant = myRestaurant) {
                                onRestaurantClick(myRestaurant.id)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Restaurantes",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    if (isEditing) {
                        item {
                            AddRestaurantCard(onClick = {
                                isCreatingNewLocal = true
                                navController.navigate(Screens.EditLocal.createRoute(-1, true))
                            })
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                    items(filteredRestaurants) { restaurant ->
                        Box {
                            RestaurantCard(restaurant = restaurant) {
                                if (isEditing) {
                                    isCreatingNewLocal = false
                                    navController.navigate(Screens.EditLocal.createRoute(restaurant.id, false))
                                } else if (!isDeleting) {
                                    onRestaurantClick(restaurant.id)
                                }
                            }
                            if (isDeleting) {
                                DeleteButton(
                                    onClick = {
                                        restaurantToDelete = restaurant
                                        showDialog = true
                                    },
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .align(Alignment.TopEnd)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun GreetingSection(userName: String) {
    Column {
        Text(
            text = "Bienvenido, $userName",
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
            horizontalArrangement = Arrangement.spacedBy(10.dp),
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
@Composable
fun AddRestaurantCard(onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF7C83C5))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AddCircleOutline,
                contentDescription = "Agregar nuevo restaurante",
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
        }
    }
}

