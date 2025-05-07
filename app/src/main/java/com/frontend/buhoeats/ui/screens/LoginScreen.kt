package com.frontend.buhoeats.ui.screens

import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.frontend.buhoeats.R

val montserratFontFamily = FontFamily(
    Font(R.font.montserrat_bold)
)

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Composable
fun Login(navControl: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var triedToSubmit by remember { mutableStateOf(false) }
    val isEmailValid = isValidEmail(email)
    val isEmailNotEmpty = email.isNotBlank()

    val isInErrorState = triedToSubmit && (!isEmailNotEmpty || !isEmailValid)
    val containerColor = if (isInErrorState) Color(0xFF999aa9) else Color.White
    val textColor = if (containerColor == Color.White) Color.Black else Color.White

    val isPasswordValid = password.isNotBlank()
    val isPasswordError = triedToSubmit && !isPasswordValid

    val passwordContainerColor = if (isPasswordError) Color(0xFF999aa9) else Color.White
    val passwordTextColor = if (passwordContainerColor == Color.White) Color.Black else Color.White





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

            Spacer(modifier = Modifier.height(0.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                isError = triedToSubmit && (!isEmailNotEmpty || !isEmailValid),
                placeholder = {
                    Text(
                        "Ingrese su correo",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        style = TextStyle(fontFamily = montserratFontFamily)
                    )
                },
                textStyle = TextStyle(
                    color = textColor,
                    fontSize = 16.sp,
                    fontFamily = montserratFontFamily
                ),
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
            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                isError = triedToSubmit && !isPasswordValid,
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
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = passwordContainerColor,
                    unfocusedContainerColor = passwordContainerColor,
                    disabledContainerColor = passwordContainerColor,
                    errorContainerColor = passwordContainerColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    triedToSubmit = true
                    when {
                        !isEmailNotEmpty -> {
                            Toast.makeText(context, "El campo de correo no debe estar vacío", Toast.LENGTH_SHORT).show()
                        }
                        !isEmailValid -> {
                            Toast.makeText(context, "Correo inválido, no es una dirección de correo", Toast.LENGTH_SHORT).show()
                        }
                        password.isBlank() -> {
                            Toast.makeText(context, "La contraseña no debe estar vacía", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                        }
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
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4285F4)
                )
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

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* Crear cuenta */ },
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
