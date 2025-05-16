package com.frontend.buhoeats.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.navigation.AppNavigator
import com.frontend.buhoeats.navigation.Screens
import com.frontend.buhoeats.utils.ValidatorUtils.isValidEmail
import com.frontend.buhoeats.ui.components.ValidationMessage
import com.frontend.buhoeats.ui.theme.montserratFontFamily

val montserratFontFamily = FontFamily(
    Font(R.font.montserrat_bold)
)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Login(navControl: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    val containerColor = Color.White
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF3D405B)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "BÚHO EATS",
                style = TextStyle(
                    fontFamily = montserratFontFamily,
                    color = Color.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    shadow = Shadow(
                        color = Color.White,
                        offset = Offset(4f, 4f),
                        blurRadius = 12f
                    )
                )
            )

            Spacer(modifier = Modifier.height(6.dp))
            Image(
                painter = painterResource(id = R.drawable.buho),
                contentDescription = "Logo Búho Eats",
                modifier = Modifier.size(190.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Correo:",
                    style = TextStyle(
                        fontFamily = montserratFontFamily,
                        color = Color.White,
                        fontSize = 24.sp
                    )
                )
            }

            TextField(
                value = email,
                onValueChange = {
                    email = it
                    if (emailError.isNotEmpty()) emailError = ""
                },
                placeholder = {
                    Text(
                        "Ingrese su correo",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        style = TextStyle(fontFamily = montserratFontFamily)
                    )
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = montserratFontFamily
                ),
                isError = emailError.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                    errorContainerColor = containerColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )

            if (emailError.isNotEmpty()) {
                ValidationMessage(message = emailError)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Contraseña:",
                    style = TextStyle(
                        fontFamily = montserratFontFamily,
                        color = Color.White,
                        fontSize = 24.sp
                    )
                )
            }

            TextField(
                value = password,
                onValueChange = {
                    password = it
                    if (passwordError.isNotEmpty()) passwordError = ""
                },
                placeholder = {
                    Text(
                        "Ingrese su contraseña",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        style = TextStyle(fontFamily = montserratFontFamily)
                    )
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = montserratFontFamily
                ),
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                    errorContainerColor = containerColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )

            if (passwordError.isNotEmpty()) {
                ValidationMessage(message = passwordError)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    var hasError = false

                    if (email.isBlank()) {
                        emailError = "El correo no puede estar vacío"
                        hasError = true
                    } else if (!isValidEmail(email)) {
                        emailError = "Correo inválido"
                        hasError = true
                    }

                    if (password.isBlank()) {
                        passwordError = "La contraseña no puede estar vacía"
                        hasError = true
                    }

                    if (!hasError) {
                        // Lógica de login (por ahora no hace nada)
                    }
                },
                modifier = Modifier
                    .width(300.dp)
                    .height(56.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF06BB0C)),
            ) {
                Text(
                    text = "Iniciar sesión",
                    style = TextStyle(
                        fontFamily = montserratFontFamily,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Sesión con Google */ },
                modifier = Modifier
                    .width(300.dp)
                    .height(56.dp)
                    .shadow(8.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4285F4))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google_image),
                        contentDescription = "Google logo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(32.dp)
                    )
                    Text(
                        text = "Inicia Sesión con Google",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "¿Todavía no tienes cuenta?",
                style = TextStyle(
                    fontFamily = montserratFontFamily,
                    color = Color.White,
                    fontSize = 20.sp
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navControl.navigate(Screens.SignUp.route) },
                modifier = Modifier
                    .width(200.dp)
                    .height(56.dp)
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF06BB0C))
            ) {
                Text(
                    text = "Crear cuenta",
                    style = TextStyle(
                        fontFamily = montserratFontFamily,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun Loginprev(){
    AppNavigator()
}