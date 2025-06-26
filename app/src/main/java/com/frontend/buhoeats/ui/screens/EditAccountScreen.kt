package com.frontend.buhoeats.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.ui.components.*
import com.frontend.buhoeats.ui.theme.AppColors
import com.frontend.buhoeats.utils.Translations
import com.frontend.buhoeats.utils.ValidatorUtils
import com.frontend.buhoeats.viewmodel.UserSessionViewModel

val montserratFontFamily = FontFamily(Font(R.font.montserrat_bold))

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditAccountScreen(
    navController: NavController,
    onBack: () -> Unit = {},
    userSessionViewModel: UserSessionViewModel
) {
    var name by remember { mutableStateOf(userSessionViewModel.currentUser.value?.name ?: "") }
    var lastname by remember { mutableStateOf(userSessionViewModel.currentUser.value?.lastName ?: "") }
    var email by remember { mutableStateOf(userSessionViewModel.currentUser.value?.email ?: "") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf("") }
    var lastnameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var newPasswordError by remember { mutableStateOf("") }
    var confirmNewPasswordError by remember { mutableStateOf("") }

    var attemptingPasswordChange by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val isGoogleUser = userSessionViewModel.currentUser.value?.password.isNullOrEmpty()

    Scaffold(
        topBar = { TopBar(showBackIcon = true, onNavClick = onBack) },
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
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = Translations.t("edit_account_title"),
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
                    userSessionViewModel.currentUser.value?.copy(imageProfile = newImageUrl.toString())?.let {
                        userSessionViewModel.updateCurrentUser(it)
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(
                label = Translations.t("name"),
                value = name,
                onValueChange = {
                    name = it
                    nameError = ""
                },
                placeholder = Translations.t("name_placeholder"),
                textColor = Color.Black,
                containerColor = Color.White
            )
            if (nameError.isNotEmpty()) ValidationMessage(message = nameError)

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = Translations.t("lastname"),
                value = lastname,
                onValueChange = {
                    lastname = it
                    lastnameError = ""
                },
                placeholder = Translations.t("lastname_placeholder"),
                textColor = Color.Black,
                containerColor = Color.White
            )
            if (lastnameError.isNotEmpty()) ValidationMessage(message = lastnameError)

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = Translations.t("email"),
                value = email,
                onValueChange = {
                    email = it
                    emailError = ""
                },
                placeholder = Translations.t("email_placeholder"),
                textColor = Color.Black,
                containerColor = Color.White,
                enabled = !isGoogleUser
            )
            if (emailError.isNotEmpty()) ValidationMessage(message = emailError)

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = Translations.t("password"),
                value = newPassword,
                onValueChange = {
                    newPassword = it
                    attemptingPasswordChange = it.isNotBlank()
                    newPasswordError = ""
                    if (confirmNewPasswordError.isNotEmpty() && newPassword == confirmNewPassword) confirmNewPasswordError = ""
                },
                placeholder = Translations.t("password_placeholder"),
                textColor = Color.Black,
                containerColor = Color.White,
                isPassword = true,
                enabled = !isGoogleUser
            )
            if (newPasswordError.isNotEmpty()) ValidationMessage(message = newPasswordError)

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = Translations.t("confirm_password"),
                value = confirmNewPassword,
                onValueChange = {
                    confirmNewPassword = it
                    confirmNewPasswordError = ""
                },
                placeholder = Translations.t("confirm_password_placeholder"),
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
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .width(150.dp)
                        .height(50.dp)
                        .padding(start = 6.dp)
                        .shadow(8.dp, RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE63946)),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text(Translations.t("cancel"), color = Color.White, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Button(
                    onClick = {
                        var hasError = false

                        if (name.isBlank()) {
                            nameError = Translations.t("error_name_required")
                            hasError = true
                        } else if (!ValidatorUtils.isOnlyLetters(name)) {
                            nameError = Translations.t("error_name_letters")
                            hasError = true
                        }

                        if (lastname.isBlank()) {
                            lastnameError = Translations.t("error_lastname_required")
                            hasError = true
                        } else if (!ValidatorUtils.isOnlyLetters(lastname)) {
                            lastnameError = Translations.t("error_lastname_letters")
                            hasError = true
                        }

                        if (email.isBlank()) {
                            emailError = Translations.t("error_email_required")
                            hasError = true
                        } else if (!ValidatorUtils.isValidEmail(email)) {
                            emailError = Translations.t("error_email_invalid")
                            hasError = true
                        }

                        if (newPassword.isNotBlank() || confirmNewPassword.isNotBlank() || attemptingPasswordChange) {
                            if (!ValidatorUtils.isSecurePassword(newPassword)) {
                                newPasswordError = Translations.t("error_password_security")
                                hasError = true
                            }
                            if (newPassword != confirmNewPassword) {
                                confirmNewPasswordError = Translations.t("error_password_mismatch")
                                hasError = true
                            }
                        }

                        if (!hasError) {
                            val passwordToSave = if (newPassword.isNotBlank()) newPassword
                            else userSessionViewModel.currentUser.value?.password.orEmpty()

                            val updatedUser = userSessionViewModel.currentUser.value?.copy(
                                name = name,
                                lastName = lastname,
                                email = email,
                                password = passwordToSave
                            )

                            updatedUser?.let {
                                userSessionViewModel.updateCurrentUser(it)
                                Toast.makeText(context, Translations.t("user_updated"), Toast.LENGTH_LONG).show()
                                navController.popBackStack()
                            }
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
                    Text(Translations.t("confirm"), color = Color.White, fontSize = 18.sp)
                }
            }
        }
    }
}
