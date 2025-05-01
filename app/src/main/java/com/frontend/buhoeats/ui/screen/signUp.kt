package com.frontend.buhoeats.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.frontend.buhoeats.R

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

val montserratFontFamily = FontFamily(
    Font(R.font.montserrat_bold)
)

@Composable
fun SignUp(navController: NavController) {

    var name by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var triedToSubmit by remember { mutableStateOf(false) }
    val isEmailValid = isValidEmail(email)
    val isEmailNotEmpty = email.isNotBlank()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF3D405B)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(14.dp))

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Regresar",
                    modifier = Modifier
                        .shadow(14.dp)
                        .size(32.dp)
                        .padding(end = 8.dp)
                        .clickable {
                            navController.navigate("login")
                        }

                )

            Spacer(modifier = Modifier.width(24.dp))

                Text(
                        text = "Crear cuenta",
                        style = TextStyle(
                            fontFamily = montserratFontFamily,
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.ExtraBold,
                            shadow = Shadow(
                                color = Color.White,
                                offset = Offset(4f, 4f),
                                blurRadius = 12f
                            )

                        )
                    )
                Spacer(modifier = Modifier.width(14.dp))

                Image(
                    painter = painterResource(id = R.drawable.buho),
                    contentDescription = "Logo Búho Eats",
                    modifier = Modifier.size(64.dp)
                )

                }
            Spacer( modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Text(
                    text = "Nombre:",
                    style = TextStyle(
                        fontFamily = montserratFontFamily,
                        color = Color.White,
                        fontSize = 24.sp
                    )
                )
            }
            Spacer( modifier = Modifier.height(12.dp))

            TextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Ingrese su nombre", color = Color.Gray, fontSize = 16.sp, style = TextStyle(fontFamily = montserratFontFamily)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                )
            )
            if (triedToSubmit && !isEmailNotEmpty) {
                Text(
                    text = "El campo no debe estar vacío",
                    color = Color.Red,
                    fontSize = 14.sp,
                    style = TextStyle(fontFamily = montserratFontFamily)
                )
            }
            Spacer( modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Text(
                    text = "Apellido:",
                    style = TextStyle(
                        fontFamily = montserratFontFamily,
                        color = Color.White,
                        fontSize = 24.sp
                    )
                )
            }
            Spacer( modifier = Modifier.height(12.dp))

            TextField(
                value = lastname,
                onValueChange = { lastname = it },
                placeholder = { Text("Ingrese su apellido", color = Color.Gray, fontSize = 16.sp, style = TextStyle(fontFamily = montserratFontFamily)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                )
            )
            if (triedToSubmit && !isEmailNotEmpty) {
                Text(
                    text = "El campo no debe estar vacío",
                    color = Color.Red,
                    fontSize = 14.sp,
                    style = TextStyle(fontFamily = montserratFontFamily)
                )
            }
            Spacer( modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Text(
                    text = "Correo:",
                    style = TextStyle(
                        fontFamily = montserratFontFamily,
                        color = Color.White,
                        fontSize = 24.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                isError = triedToSubmit && (!isEmailNotEmpty || !isEmailValid),
                placeholder = { Text("ingrese su correo", color = Color.Gray, fontSize = 16.sp ,style = TextStyle(fontFamily = montserratFontFamily)) },

                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                )
            )
            if (triedToSubmit && !isEmailNotEmpty) {
                Text(
                    text = "El campo no debe estar vacío",
                    color = Color.Red,
                    fontSize = 14.sp,
                    style = TextStyle(fontFamily = montserratFontFamily)
                )
            }
            else if (triedToSubmit && !isEmailValid) {
                Text(
                    text = "Correo inválido, no es una direccion de correo",
                    color = Color.Red,
                    fontSize = 14.sp,
                    style = TextStyle(fontFamily = montserratFontFamily)
                )
            }




        }
        }
    }
