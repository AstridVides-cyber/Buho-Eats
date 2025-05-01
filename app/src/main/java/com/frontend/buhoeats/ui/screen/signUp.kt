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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
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
    var confirmPassword by remember { mutableStateOf("") }


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
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
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
                placeholder = { Text("Ingrese su contraseña", color = Color.Gray, fontSize = 16.sp, style = TextStyle(fontFamily = montserratFontFamily)) },
                visualTransformation = PasswordVisualTransformation(),
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

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Text(
                    text = "Confirme su contraseña:",
                    style = TextStyle(
                        fontFamily = montserratFontFamily,
                        color = Color.White,
                        fontSize = 24.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = { Text("Confirme su contraseña", color = Color.Gray, fontSize = 16.sp, style = TextStyle(fontFamily = montserratFontFamily)) },
                visualTransformation = PasswordVisualTransformation(),
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
            if (triedToSubmit && password != confirmPassword) {
                Text(
                    text = "Las contraseñas no coinciden",
                    color = Color.Red,
                    fontSize = 14.sp,
                    style = TextStyle(fontFamily = montserratFontFamily)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick =  {
                    triedToSubmit = true
                    if (isEmailValid) { }
                },
                modifier = Modifier.width(300.dp).height(56.dp).shadow(elevation= 8.dp, shape= RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF06BB0C))
            ) {
                Text(
                    text = "Registrarte",
                    style = TextStyle(fontFamily = montserratFontFamily, fontSize = 20.sp, color = Color.White)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /*sesión con Google */ },
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


            Row {
                Text(text = "¿Ya tienes una cuenta? ",color = Color.White,
                    fontSize = 16.sp,fontFamily = montserratFontFamily)
                Text(
                    text = "Iniciar Sesión",
                    color = Color(0xFF0084FF),
                    fontSize = 16.sp,
                    fontFamily = montserratFontFamily,
                    modifier = Modifier.clickable {
                        navController.navigate("login")
                    },
                    fontWeight = FontWeight.Bold
                )
            }


        }
        }
    }
