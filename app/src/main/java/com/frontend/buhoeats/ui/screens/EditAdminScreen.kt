package com.frontend.buhoeats.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.FormField
import com.frontend.buhoeats.ui.components.TopBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.frontend.buhoeats.viewmodel.RestaurantViewModel
import com.frontend.buhoeats.viewmodel.UserSessionViewModel
import androidx.compose.runtime.LaunchedEffect
import com.frontend.buhoeats.ui.components.ValidationMessage
import com.frontend.buhoeats.utils.ValidatorUtils.isValidPhoneNumber

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
        topBar = {
            TopBar(showBackIcon = true) { navController.popBackStack() }
        },
        bottomBar = {
            BottomNavigationBar(navController)
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
                    .verticalScroll(scrollState)
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Editar Información del Restaurante",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormField(
                    label = "Correo:",
                    value = email,
                    onValueChange = { email = it; emailError = false },
                    isError = emailError
                )
                if (emailError) {
                    ValidationMessage(if (email.isBlank()) "El correo no puede estar vacío" else "El formato del correo no es válido")
                }

                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = "Teléfono:",
                    value = phone,
                    onValueChange = { phone = it; phoneError = false },
                    isError = phoneError
                )
                if (phoneError) {
                    ValidationMessage(if (phone.isBlank()) "El teléfono no puede estar vacío" else "El formato del teléfono no es válido")
                }

                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = "Horario:",
                    value = schedule,
                    onValueChange = { schedule = it; scheduleError = false },
                    isError = scheduleError
                )
                if (scheduleError) {
                    ValidationMessage("El horario no puede estar vacío")
                }

                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = "Dirección:",
                    value = address,
                    onValueChange = { address = it; addressError = false },
                    isMultiline = true,
                    isError = addressError
                )
                if (addressError) {
                    ValidationMessage("La dirección no puede estar vacía")
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
                        Text("Cancelar", color = Color.White, fontSize = 16.sp)
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
                                Toast.makeText(context, "Información editada exitosamente", Toast.LENGTH_SHORT).show()
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
                        Text("Confirmar", color = Color.White, fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
