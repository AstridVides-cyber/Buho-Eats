package com.frontend.buhoeats.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.frontend.buhoeats.R
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.frontend.buhoeats.models.User
import com.frontend.buhoeats.navigation.Screens
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.CustomTextField
import com.frontend.buhoeats.ui.components.ProfileImage
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.utils.ValidatorUtils
import com.frontend.buhoeats.ui.components.ValidationMessage


val montserratFontFamily = FontFamily(
    Font(R.font.montserrat_bold)
)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun myAccount(navController: NavController, user: User, onBack: () -> Unit = {}) {

    var name by remember { mutableStateOf(user.name) }
    var lastname by remember { mutableStateOf(user.lastName) }
    var email by remember { mutableStateOf(user.email) }
    var password by remember { mutableStateOf(user.password) }
    var confirmPassword by remember { mutableStateOf(user.confirmpassword) }

    var triedToSubmit by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf("") }
    var lastnameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopBar(showBackIcon = true , onNavClick = onBack
        ) },
        bottomBar = { BottomNavigationBar() },
        containerColor = Color(0xFF3D405B)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 40.dp, vertical = 20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        )

        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Mi cuenta",
                    fontFamily = montserratFontFamily,
                    color = Color.White,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.ExtraBold
                )


            }
                Spacer(modifier = Modifier.width(14.dp))

                ProfileImage(
                    userImageUrl = user.imageProfile,
                    onImageSelected = { }
                )


            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(
                label = "Nombre",
                value = name,
                onValueChange = {
                    name = it
                    if (nameError.isNotEmpty()) nameError = ""
                },
                placeholder = "Ingrese su nombre",
                textColor = Color.Black,
                containerColor = Color.White,
            )
            if (nameError.isNotEmpty()) ValidationMessage(message = nameError)

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = "Apellido",
                value = lastname,
                onValueChange = {
                    lastname = it
                    if (lastnameError.isNotEmpty()) lastnameError = ""
                },
                placeholder = "Ingrese su apellido",
                textColor = Color.Black,
                containerColor = Color.White
            )
            if (lastnameError.isNotEmpty()) ValidationMessage(message = lastnameError)

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = "Correo",
                value = email,
                onValueChange = {
                    email = it
                    if (emailError.isNotEmpty()) emailError = ""
                },
                placeholder = "Ingrese su correo",
                textColor = Color.Black,
                containerColor = Color.White
            )
            if (emailError.isNotEmpty()) ValidationMessage(message = emailError)

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = "Contraseña",
                value = password,
                onValueChange = {
                    password = it
                    if (passwordError.isNotEmpty()) passwordError = ""
                },
                placeholder = "Ingrese su contraseña",
                textColor = Color.Black,
                containerColor = Color.White,
                isPassword = true
            )
            if (passwordError.isNotEmpty()) ValidationMessage(message = passwordError)

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = "Confirmar contraseña",
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    if (confirmPasswordError.isNotEmpty()) confirmPasswordError = ""
                },
                placeholder = "Repita su contraseña",
                textColor = Color.Black,
                containerColor = Color.White,
                isPassword = true
            )
            if (confirmPasswordError.isNotEmpty()) ValidationMessage(message = confirmPasswordError)

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(50.dp)
                        .padding(start = 6.dp)
                        .shadow(8.dp, RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE63946))
                ) {
                    Text("Cancelar", color = Color.White, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = {
                    triedToSubmit = true
                    var hasError = false

                    if (name.isBlank()) {
                        nameError = "El nombre no debe estar vacío"
                        hasError = true
                    } else if (!ValidatorUtils.isOnlyLetters(name)) {
                        nameError = "El nombre solo debe contener letras"
                        hasError = true
                    }

                    if (lastname.isBlank()) {
                        lastnameError = "El apellido no debe estar vacío"
                        hasError = true
                    } else if (!ValidatorUtils.isOnlyLetters(lastname)) {
                        lastnameError = "El apellido solo debe contener letras"
                        hasError = true
                    }

                    if (email.isBlank()) {
                        emailError = "El correo no debe estar vacío"
                        hasError = true
                    } else if (!ValidatorUtils.isValidEmail(email)) {
                        emailError = "Correo inválido"
                        hasError = true
                    }

                    if (password.isBlank()) {
                        passwordError = "La contraseña no debe estar vacía"
                        hasError = true
                    }

                    if (confirmPassword.isBlank()) {
                        confirmPasswordError = "Debe confirmar la contraseña"
                        hasError = true
                    } else if (password != confirmPassword) {
                        confirmPasswordError = "Las contraseñas no coinciden"
                        hasError = true
                    }

                    if (!hasError) { navController.navigate(Screens.Profile.route)
                    }
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(50.dp)
                        .padding(end = 6.dp)
                        .shadow(8.dp, RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF06BB0C))
                ) {
                    Text("Confirmar", color = Color.White, fontSize = 18.sp)
                }


            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview()
@Composable
fun MyAccountPreview() {
    val fakeUser = User(
        id = 1,
        name = "Michelle",
        lastName = "Maltez",
        email = "michelle@correo.com",
        password = "lol",
        confirmpassword = "lol",
        imageProfile = "foto"
    )
    myAccount(navController = rememberNavController(), user = fakeUser)
}
