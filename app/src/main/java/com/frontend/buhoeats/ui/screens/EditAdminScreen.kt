package com.frontend.buhoeats.ui.screens


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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.navigation.Screens
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.FormField
import com.frontend.buhoeats.ui.components.PromoCard
import com.frontend.buhoeats.ui.components.TopBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue



@Composable
fun EditInfo(navController: NavController) {
    val scrollState = rememberScrollState()

    var email by remember { mutableStateOf("Vides@gmail.com") }
    var phone by remember { mutableStateOf("1111-1111") }
    var schedule by remember { mutableStateOf("Lunes a Viernes de 9 AM - 12 PM") }
    var address by remember { mutableStateOf("Vivo en la esquina con mi pollito") }

    val originalEmail = remember { email }
    val originalPhone = remember { phone }
    val originalSchedule = remember { schedule }
    val originalAddress = remember { address }

    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    var scheduleError by remember { mutableStateOf(false) }
    var addressError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(showBackIcon = true) {
                navController.popBackStack()
            }
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
                    text = "Editar Información:",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(24.dp))

                FormField(
                    label = "Correo:",
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = false
                    },
                    isError = emailError,
                    errorMessage = "El correo no puede estar vacío"
                )
                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = "Teléfono:",
                    value = phone,
                    onValueChange = {
                        phone = it
                        phoneError = false
                    },
                    isError = phoneError,
                    errorMessage = "El teléfono no puede estar vacío"
                )
                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = "Horario:",
                    value = schedule,
                    onValueChange = {
                        schedule = it
                        scheduleError = false
                    },
                    isError = scheduleError,
                    errorMessage = "El horario no puede estar vacío"
                )
                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = "Dirección:",
                    value = address,
                    onValueChange = {
                        address = it
                        addressError = false
                    },
                    isMultiline = true,
                    isError = addressError,
                    errorMessage = "La dirección no puede estar vacía"
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            email = originalEmail
                            phone = originalPhone
                            schedule = originalSchedule
                            address = originalAddress

                            emailError = false
                            phoneError = false
                            scheduleError = false
                            addressError = false
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC11D0C)),
                        modifier = Modifier.weight(1f).padding(end = 8.dp)
                    ) {
                        Text(text = "Cancelar", color = Color.White)
                    }

                    Button(
                        onClick = {
                            emailError = email.isBlank()
                            phoneError = phone.isBlank()
                            scheduleError = schedule.isBlank()
                            addressError = address.isBlank()

                            val hasErrors = emailError || phoneError || scheduleError || addressError

                            if (!hasErrors) {
                                println("Guardado: $email, $phone, $schedule, $address")
                            }
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF06BB0C)),
                        modifier = Modifier.weight(1f).padding(start = 8.dp)
                    ) {
                        Text(text = "Confirmar", color = Color.White)
                    }
                }
            }
        }
    }
}



