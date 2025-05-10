package com.frontend.buhoeats.ui.screens

import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.frontend.buhoeats.ui.components.CustomTextField


fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
val montserratFontFamily = FontFamily(
    Font(R.font.montserrat_bold)
)

@Composable
fun SignUp(navController: NavController) {

    val context = LocalContext.current

    var lastname by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


    var triedToSubmit by remember { mutableStateOf(false) }

    //name
    var name by remember { mutableStateOf("") }
    val isNameNotEmpty = name.isNotBlank()
    val isNameError = triedToSubmit && !isNameNotEmpty
    val nameContainerColor = if (isNameError) Color(0xFF999aa9) else Color.White
    val nameTextColor = if (nameContainerColor == Color.White) Color.Black else Color.White

    //last name
    val isLastnameNotEmpty = lastname.isNotBlank()
    val isLastnameError = triedToSubmit && !isLastnameNotEmpty
    val lastnameContainerColor = if (isLastnameError) Color(0xFF999aa9) else Color.White
    val lastnameTextColor = if (lastnameContainerColor == Color.White) Color.Black else Color.White

    //password
    val isPasswordNotEmpty = password.isNotBlank()
    val isPasswordError = triedToSubmit && !isPasswordNotEmpty
    val passwordContainerColor = if (isPasswordError) Color(0xFF999aa9) else Color.White
    val passwordTextColor = if (passwordContainerColor == Color.White) Color.Black else Color.White

    //confirmpassword
    val isConfirmPasswordNotEmpty = confirmPassword.isNotBlank()
    val isConfirmPasswordMatch = password == confirmPassword
    val isConfirmPasswordError = triedToSubmit && (!isConfirmPasswordNotEmpty || !isConfirmPasswordMatch)
    val confirmPasswordContainerColor = if (isConfirmPasswordError) Color(0xFF999aa9) else Color.White
    val confirmPasswordTextColor = if (confirmPasswordContainerColor == Color.White) Color.Black else Color.White

    //gmail
    val isEmailValid = isValidEmail(email)
    val isEmailNotEmpty = email.isNotBlank()
    val isInErrorState = triedToSubmit && (!isEmailNotEmpty || !isEmailValid)
    val containerColor = if (isInErrorState) Color(0xFF999aa9) else Color.White
    val textColor = if (containerColor == Color.White) Color.Black else Color.White


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF3D405B)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(32.dp)  .verticalScroll(rememberScrollState()),
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
                    modifier = Modifier.size(50.dp)
                )

                }
            Spacer( modifier = Modifier.height(24.dp))

            CustomTextField(
                label = "Nombre",
                value = name,
                onValueChange = { name = it },
                placeholder = "Ingrese su nombre",
                textColor = nameTextColor,
                containerColor = nameContainerColor,
                validateDigits = true,
                contextMessage = "El nombre no puede contener números"
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = "Apellido",
                value = lastname,
                onValueChange = { lastname = it },
                placeholder = "Ingrese su apellido",
                textColor = lastnameTextColor,
                containerColor = lastnameContainerColor,
                validateDigits = true,
                contextMessage = "El apellido no puede contener números"
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = "Correo",
                value = email,
                onValueChange = { email = it },
                placeholder = "Ingrese su correo",
                textColor = textColor,
                containerColor = containerColor
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = "Contraseña",
                value = password,
                onValueChange = { password = it },
                placeholder = "Ingrese su contraseña",
                textColor = passwordTextColor,
                containerColor = passwordContainerColor,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = "Confirmar contraseña",
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                placeholder = "Repita su contraseña",
                textColor = confirmPasswordTextColor,
                containerColor = confirmPasswordContainerColor,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    triedToSubmit = true
                    when {
                        !isNameNotEmpty -> {
                            Toast.makeText(context, "El nombre no debe estar vacío", Toast.LENGTH_SHORT).show()
                        }
                        !isLastnameNotEmpty -> {
                            Toast.makeText(context, "El apellido no debe estar vacío", Toast.LENGTH_SHORT).show()
                        }
                        !isEmailNotEmpty -> {
                            Toast.makeText(context, "El correo no debe estar vacío", Toast.LENGTH_SHORT).show()
                        }
                        !isEmailValid -> {
                            Toast.makeText(context, "Correo inválido, no es una dirección de correo válida", Toast.LENGTH_SHORT).show()
                        }
                        !isPasswordNotEmpty -> {
                            Toast.makeText(context, "La contraseña no debe estar vacía", Toast.LENGTH_SHORT).show()
                        }
                        !isConfirmPasswordNotEmpty -> {
                            Toast.makeText(context, "Debe confirmar su contraseña", Toast.LENGTH_SHORT).show()
                        }
                        !isConfirmPasswordMatch -> {
                            Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(context, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()
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
