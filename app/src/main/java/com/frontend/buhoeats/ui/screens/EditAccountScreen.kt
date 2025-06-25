package com.frontend.buhoeats.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.CustomTextField
import com.frontend.buhoeats.ui.components.ProfileImage
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.utils.ValidatorUtils
import com.frontend.buhoeats.ui.components.ValidationMessage
import com.frontend.buhoeats.ui.theme.AppColors
import com.frontend.buhoeats.viewmodel.UserSessionViewModel
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

val montserratFontFamily = FontFamily(
    Font(R.font.montserrat_bold)
)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditAccountScreen(navController: NavController,
    onBack: () -> Unit = {}, userSessionViewModel: UserSessionViewModel
) {

    var name by remember { mutableStateOf(userSessionViewModel.currentUser.value?.name ?: "") }
    var lastname by remember { mutableStateOf(userSessionViewModel.currentUser.value?.lastName ?: "") }
    var email by remember { mutableStateOf(userSessionViewModel.currentUser.value?.email ?: "") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }

    var triedToSubmit by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var attemptingPasswordChange by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf("") }
    var lastnameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var newPasswordError by remember { mutableStateOf("") }
    var confirmNewPasswordError by remember { mutableStateOf("") }

    val isGoogleUser = userSessionViewModel.currentUser.value?.password.isNullOrEmpty()


    Scaffold(
        topBar = { TopBar(showBackIcon = true , onNavClick = onBack
        ) },
        bottomBar = { BottomNavigationBar(navController) },
        containerColor = AppColors.primary
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
                userImageUrl = userSessionViewModel.currentUser.value?.imageProfile ?: "",
                onImageSelected = { newImageUrl ->
                    val updatedUser = userSessionViewModel.currentUser.value?.copy(
                        imageProfile = newImageUrl.toString()
                    )
                    if (updatedUser != null) {
                        userSessionViewModel.updateCurrentUser(updatedUser)
                    }
                }
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
                containerColor = Color.White,
                enabled = !isGoogleUser
            )
            if (emailError.isNotEmpty()) ValidationMessage(message = emailError)

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = "Contraseña",
                value = newPassword,
                onValueChange = {
                    newPassword = it
                    attemptingPasswordChange = it.isNotBlank() // El usuario está intentando cambiarla si escribe algo
                    if (newPasswordError.isNotEmpty()) newPasswordError = ""
                    if (confirmNewPasswordError.isNotEmpty() && newPassword == confirmNewPassword) confirmNewPasswordError = ""
                },
                placeholder = "Ingrese su contraseña",
                textColor = Color.Black,
                containerColor = Color.White,
                isPassword = true,
                enabled = !isGoogleUser
            )
            if (newPasswordError.isNotEmpty()) ValidationMessage(message = newPasswordError)
            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = "Confirmar contraseña",
                value = confirmNewPassword,
                onValueChange = {
                    confirmNewPassword = it
                    if (confirmNewPasswordError.isNotEmpty()) confirmNewPasswordError = ""
                },
                placeholder = "Repita su contraseña",
                textColor = Color.Black,
                containerColor = Color.White,
                isPassword = true,
                enabled = !isGoogleUser
            )
            if (confirmNewPasswordError.isNotEmpty()) ValidationMessage(message = confirmNewPasswordError)

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
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE63946)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
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

                        if (newPassword.isNotBlank() || confirmNewPassword.isNotBlank() || attemptingPasswordChange) {
                            if (!ValidatorUtils.isSecurePassword(newPassword)) {
                                newPasswordError = "La contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula, un número y un símbolo"

                                hasError = true
                            } else {
                                newPasswordError = ""
                            }

                            if (newPassword != confirmNewPassword) {
                                confirmNewPasswordError = "La contraseñas no coinciden"
                                hasError = true
                            } else {
                                confirmNewPasswordError = ""
                            }
                        } else {
                            newPasswordError = ""
                            confirmNewPasswordError = ""
                        }

                        if (!hasError) {
                            val passwordToSave = if (newPassword.isNotBlank() && attemptingPasswordChange) {
                                newPassword
                            } else {
                                userSessionViewModel.currentUser.value!!.password
                            }
                            val updatedUser = userSessionViewModel.currentUser.value?.copy(
                                name = name,
                                lastName = lastname,
                                email = email,
                                password = passwordToSave
                            )

                            if (updatedUser != null) {
                                userSessionViewModel.updateCurrentUser(updatedUser)
                            }
                            Toast.makeText(
                                context,
                                "Usuario actualizado correctamente",
                                Toast.LENGTH_LONG
                            ).show()
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(50.dp)
                        .padding(end = 6.dp)
                        .shadow(8.dp, RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF06BB0C)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text("Confirmar", color = Color.White, fontSize = 18.sp)
                }
                Spacer(modifier = Modifier.height(12.dp))


            }
        }
    }
}