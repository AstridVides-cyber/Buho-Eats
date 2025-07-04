package com.frontend.buhoeats.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import android.widget.Toast
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.frontend.buhoeats.R
import com.frontend.buhoeats.ui.components.ContactCard
import com.frontend.buhoeats.ui.components.DishCard
import androidx.navigation.NavController
import com.frontend.buhoeats.models.Comment
import com.frontend.buhoeats.models.Dish
import com.frontend.buhoeats.models.Rating
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.models.User
import com.frontend.buhoeats.navigation.Screens
import com.frontend.buhoeats.ui.components.ConfirmationDialog
import com.frontend.buhoeats.ui.components.EditFloatingButton
import com.frontend.buhoeats.ui.components.ValidationMessage
import com.frontend.buhoeats.ui.theme.ThemeManager
import com.frontend.buhoeats.utils.Translations
import com.frontend.buhoeats.viewmodel.FavoritesViewModel
import com.frontend.buhoeats.viewmodel.FavoritesViewModelFactory
import com.frontend.buhoeats.viewmodel.RestaurantViewModel
import com.frontend.buhoeats.viewmodel.UserSessionViewModel
import com.frontend.buhoeats.utils.ImageConverter
import androidx.compose.ui.graphics.asImageBitmap

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RestaurantScreen(
    navController: NavController,
    restaurant: Restaurant,
    userSessionViewModel: UserSessionViewModel,
    restaurantViewModel: RestaurantViewModel
) {
    val currentUser = userSessionViewModel.currentUser.value
    val isAdminOfThisRestaurant = currentUser?.rol == "admin" && restaurant.admin == currentUser.id


    val backgroundImage = if (ThemeManager.isDarkTheme)
        painterResource(id = R.drawable.backgrounddark)
    else
        painterResource(id = R.drawable.backgroundlighttheme)

    Scaffold(
        topBar = {
            TopBar(
                showBackIcon = true,
                onNavClick = { navController.popBackStack() }

            )
        },
        bottomBar = { BottomNavigationBar(navController) },
        floatingActionButton = {
            if (isAdminOfThisRestaurant) {
                EditFloatingButton(
                    onClick = {navController.navigate(Screens.EditRestaurant.route)}
                )
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Image(
                painter = backgroundImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            var selectedRestaurant by remember { mutableStateOf(restaurant) }

            RestaurantContent(
                modifier = Modifier.fillMaxSize(),
                restaurant = selectedRestaurant,
                favoritesViewModel = remember {
                    FavoritesViewModel(userSessionViewModel)
                },
                currentUser = userSessionViewModel.currentUser.value,
                userSessionViewModel = userSessionViewModel,
                onUpdate = { updatedRestaurant ->
                    selectedRestaurant = updatedRestaurant
                },
                restaurantViewModel = restaurantViewModel
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RestaurantContent(
    restaurant: Restaurant,
    modifier: Modifier = Modifier,
    favoritesViewModel: FavoritesViewModel = viewModel(
        factory = FavoritesViewModelFactory(
            userSessionViewModel = viewModel()
        )
    ),
    currentUser: User? = null,
    userSessionViewModel: UserSessionViewModel,
    onUpdate: (Restaurant) -> Unit,
    restaurantViewModel: RestaurantViewModel
) {
    val favoriteIds by favoritesViewModel.favoriteRestaurantIds.collectAsState()
    val isFavorite = favoriteIds.contains(restaurant.id)
    val isAdminOfThisRestaurant = currentUser?.rol == "admin" && restaurant.admin == currentUser.id
    val isAdmin = currentUser?.rol == "admin" && restaurant.admin != currentUser.id
    val isSuperAdmin = currentUser?.rol == "superadmin"

    val menuList = restaurant.menu

    var rating by rememberSaveable { mutableIntStateOf(0) }
    var comment by remember { mutableStateOf("") }
    restaurant.ratings.find { it.userId == currentUser?.id }
    var dishToDelete by remember { mutableStateOf<Dish?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    var showRatingErrorMessage by remember { mutableStateOf(false) }
    var showCommentErrorMessage by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val user = userSessionViewModel.users.value.find { it.id == currentUser?.id }
    user?.let { "${it.name} ${it.lastName}" } ?: Translations.t("unknown_user")

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

            if (currentUser?.rol != "admin" && currentUser?.rol != "superadmin") {
                IconButton(onClick = {
                    favoritesViewModel.toggleFavorite(restaurant.id)
                }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (isFavorite) Translations.t("favoritesRemove") else Translations.t("favoritesAdd"),
                        tint = if (isFavorite) Color.Red else Color.Gray,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            // CAMBIO MÍNIMO: Detectar tipo de imagen automáticamente
            when {
                ImageConverter.isBase64(restaurant.imageUrl) -> {
                    // Es Base64 - convertir a Bitmap
                    val bitmap = ImageConverter.base64ToBitmap(restaurant.imageUrl)
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap.asImageBitmap(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    } else {
                        // Fallback si falla la conversión
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp)
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            Text("Error loading image")
                        }
                    }
                }
                restaurant.imageUrl.isNotBlank() -> {
                    // Es URL - usar AsyncImage como antes
                    AsyncImage(
                        model = restaurant.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                }
                else -> {
                    // Sin imagen
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        Text("No image")
                    }
                }
            }
        }

        ContactCard(contactInfo = restaurant.contactInfo)

        Spacer(modifier = Modifier.padding(10.dp))

        Text(
            Translations.t("dailyMenu"),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))

        menuList.forEach { dish ->
            DishCard(
                dish = dish,
                showDelete = isAdminOfThisRestaurant,
                onDelete = {
                    dishToDelete = dish
                    showDialog = true
                }
            )
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        if (!isAdminOfThisRestaurant) {
            if (!isSuperAdmin){
                if (!isAdmin) {
                    if (!restaurant.blockedUsers.contains(currentUser?.id)) {
                        val existingRating = restaurant.ratings.find { it.userId == currentUser?.id }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (existingRating == null) {
                                Text(
                                    text = Translations.t("rateApp"),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                RatingBar(rating = rating, onRatingChanged = { rating = it })

                                Spacer(modifier = Modifier.height(10.dp))
                            }
                            Text(Translations.t("writeOpinion"), fontSize = 16.sp, color = Color.Black, modifier = Modifier.padding(bottom = 10.dp))
                            OutlinedTextField(
                                value = comment,
                                onValueChange = { comment = it },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                                    focusedContainerColor = Color.White.copy(alpha = 0.95f)
                                )
                            )

                            Spacer(modifier = Modifier.size(10.dp))

                            if (showRatingErrorMessage) {
                                ValidationMessage(
                                    message = Translations.t("selectRatingError"),
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                            if (showCommentErrorMessage) {
                                ValidationMessage(
                                    message = Translations.t("emptyCommentError"),
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                            Button(
                                onClick = {
                                    showRatingErrorMessage = false
                                    showCommentErrorMessage = false

                                    val user = userSessionViewModel.users.value.find { it.id == currentUser?.id }
                                    var isValid = true

                                    if (existingRating == null && rating == 0) {
                                        showRatingErrorMessage = true
                                        isValid = false
                                    }

                                    if (comment.isBlank()) {
                                        showCommentErrorMessage = true
                                        isValid = false
                                    }

                                    if (user != null && isValid) {
                                        val updatedComments = restaurant.comments.toMutableList().apply {
                                            if (comment.isNotBlank()) {
                                                add(Comment(userId = user.id, comment = comment))
                                            }
                                        }

                                        val updatedRatings = restaurant.ratings.toMutableList().apply {
                                            if (existingRating == null && rating > 0) {
                                                add(Rating(userId = user.id, rating = rating))
                                            }
                                        }

                                        val updatedRestaurant = restaurant.copy(
                                            comments = updatedComments,
                                            ratings = updatedRatings
                                        )

                                        restaurantViewModel.updateRestaurant(updatedRestaurant)
                                        onUpdate(updatedRestaurant)

                                        comment = ""
                                        rating = 0
                                    }
                                },
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .padding(5.dp)
                            ) {
                                Text(Translations.t("submitReview"))
                            }

                            Spacer(modifier = Modifier.size(10.dp))

                            if (existingRating != null) {
                                Text(
                                    text = Translations.t("alreadyRated").format(existingRating.rating),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        }


                    } else {
                        Text(
                            text = Translations.t("blockedMessage"),
                            color = Color.Red,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                }
            }
            Spacer(modifier = Modifier.size(10.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    Translations.t("ratingsAndComments"),
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.size(15.dp))

                val visibleComments = restaurant.comments.filterNot { comment ->
                    restaurant.blockedUsers.contains(comment.userId)
                }

                visibleComments.forEach { comment ->
                    val user = userSessionViewModel.users.value.find { it.id == comment.userId }
                    val userRating = restaurant.ratings.find { it.userId == comment.userId }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                    ) {
                        Opinion(user, comment, userRating)
                    }
                }
            }
        }
        if (showDialog && dishToDelete != null) {
            ConfirmationDialog(
                message = Translations.t("deleteDishConfirmation"),
                onConfirm = {
                    dishToDelete?.let { dish ->
                        restaurantViewModel.removeDishFromRestaurant(restaurant.id, dish.id)
                        val updatedRestaurant = restaurantViewModel.restaurantList.find { it.id == restaurant.id }
                        updatedRestaurant?.let { onUpdate(it) }
                        Toast.makeText(context, Translations.t("deleteDishSuccess"), Toast.LENGTH_SHORT).show()
                    }
                    showDialog = false
                    dishToDelete = null
                },
                onDismiss = {
                    showDialog = false
                    dishToDelete = null
                }
            )
        }

    }
}
