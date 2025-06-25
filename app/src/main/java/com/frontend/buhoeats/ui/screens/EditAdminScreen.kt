package com.frontend.buhoeats.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.data.InMemoryUserDataSource
import com.frontend.buhoeats.ui.components.*
import com.frontend.buhoeats.ui.theme.AppColors
import com.frontend.buhoeats.ui.theme.ThemeManager
import com.frontend.buhoeats.utils.Translations
import com.frontend.buhoeats.utils.ValidatorUtils.isValidPhoneNumber
import com.frontend.buhoeats.viewmodel.RestaurantViewModel
import com.frontend.buhoeats.viewmodel.UserSessionViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditInfoAdmin(
    navController: NavController,
    userSessionViewModel: UserSessionViewModel,
    restaurantViewModel: RestaurantViewModel
) {
    val currentUser = userSessionViewModel.currentUser.value
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val restaurant = InMemoryUserDataSource.getRestaurants().find { it.admin == currentUser?.id }

    if (currentUser?.rol != "admin" || restaurant == null) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
        return
    }

    var email by remember { mutableStateOf(restaurant.contactInfo.email) }
    var phone by remember { mutableStateOf(restaurant.contactInfo.phone) }
    var schedule by remember { mutableStateOf(restaurant.contactInfo.hours) }
    var address by remember { mutableStateOf(restaurant.contactInfo.address) }

    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    var scheduleError by remember { mutableStateOf(false) }
    var addressError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopBar(showBackIcon = true) { navController.popBackStack() } },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val backgroundImage = if (ThemeManager.isDarkTheme)
                painterResource(id = R.drawable.backgrounddark)
            else
                painterResource(id = R.drawable.backgroundlighttheme)

            Image(
                painter = backgroundImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Text(
                    text = Translations.t("edit_restaurant_info"),
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.texto,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormField(
                    label = Translations.t("email") + ":",
                    value = email,
                    onValueChange = { email = it; emailError = false },
                    isError = emailError
                )
                if (emailError) {
                    ValidationMessage(
                        if (email.isBlank()) Translations.t("error_email_required")
                        else Translations.t("error_email_invalid")
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = Translations.t("phone") + ":",
                    value = phone,
                    onValueChange = { phone = it; phoneError = false },
                    isError = phoneError
                )
                if (phoneError) {
                    ValidationMessage(
                        if (phone.isBlank()) Translations.t("error_phone_required")
                        else Translations.t("error_phone_invalid")
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = Translations.t("schedule") + ":",
                    value = schedule,
                    onValueChange = { schedule = it; scheduleError = false },
                    isError = scheduleError
                )
                if (scheduleError) {
                    ValidationMessage(Translations.t("error_schedule_required"))
                }

                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = Translations.t("address") + ":",
                    value = address,
                    onValueChange = { address = it; addressError = false },
                    isMultiline = true,
                    isError = addressError
                )
                if (addressError) {
                    ValidationMessage(Translations.t("error_address_required"))
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            email = restaurant.contactInfo.email
                            phone = restaurant.contactInfo.phone
                            schedule = restaurant.contactInfo.hours
                            address = restaurant.contactInfo.address

                            emailError = false
                            phoneError = false
                            scheduleError = false
                            addressError = false

                            navController.popBackStack()
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC11D0C)),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .height(50.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Text(Translations.t("cancel"), color = Color.White, fontSize = 16.sp)
                    }

                    Button(
                        onClick = {
                            emailError = email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                            phoneError = phone.isBlank() || !isValidPhoneNumber(phone)
                            scheduleError = schedule.isBlank()
                            addressError = address.isBlank()

                            if (!emailError && !phoneError && !scheduleError && !addressError) {
                                val updatedRestaurant = restaurant.copy(
                                    contactInfo = restaurant.contactInfo.copy(
                                        email = email,
                                        phone = phone,
                                        hours = schedule,
                                        address = address
                                    )
                                )
                                restaurantViewModel.updateRestaurant(updatedRestaurant)
                                Toast.makeText(
                                    context,
                                    Translations.t("info_updated_success"),
                                    Toast.LENGTH_SHORT
                                ).show()
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
                        Text(Translations.t("confirm"), color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
