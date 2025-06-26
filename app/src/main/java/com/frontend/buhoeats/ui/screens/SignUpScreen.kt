package com.frontend.buhoeats.ui.screens

import com.frontend.buhoeats.ui.theme.AppColors
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.graphics.Color
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.frontend.buhoeats.auth.getGoogleSignInClient
import com.frontend.buhoeats.models.User
import com.frontend.buhoeats.navigation.Screens
import com.frontend.buhoeats.ui.components.CustomTextField
import com.frontend.buhoeats.utils.ValidatorUtils
import com.frontend.buhoeats.ui.components.ValidationMessage
import com.frontend.buhoeats.utils.Translations
import com.frontend.buhoeats.viewmodel.UserSessionViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignUp(navController: NavController,
           userSessionViewModel: UserSessionViewModel
) {

    var name by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var triedToSubmit by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf("") }
    var lastnameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    val context = LocalContext.current


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppColors.primary
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Regresar",
                    modifier = Modifier
                        .shadow(14.dp)
                        .size(32.dp)
                        .padding(end = 8.dp)
                        .clickable {
                            navController.navigate(Screens.Login.route)
                        }
                        .graphicsLayer { rotationY = 180f }

                )
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = Translations.t("create_account"),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(14.dp))
                Image(
                    painter = painterResource(id = R.drawable.buho),
                    contentDescription = "Logo Búho Eats",
                    modifier = Modifier.size(50.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            CustomTextField(
                label = Translations.t("name"),
                value = name,
                onValueChange = {
                    name = ValidatorUtils.capitalizeWords(it)
                    if (nameError.isNotEmpty()) nameError = ""
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
                    lastname = ValidatorUtils.capitalizeWords(it)
                    if (lastnameError.isNotEmpty()) lastnameError = ""
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
                    if (emailError.isNotEmpty()) emailError = ""
                },
                placeholder = Translations.t("email_placeholder"),
                textColor = Color.Black,
                containerColor = Color.White
            )
            if (emailError.isNotEmpty()) ValidationMessage(message = emailError)

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = Translations.t("password"),
                value = password,
                onValueChange = {
                    password = it
                    if (passwordError.isNotEmpty()) passwordError = ""
                },
                placeholder = Translations.t("password_placeholder"),
                textColor = Color.Black,
                containerColor = Color.White,
                isPassword = true
            )
            if (passwordError.isNotEmpty()) ValidationMessage(message = passwordError)

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = Translations.t("confirm_password"),
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    if (confirmPasswordError.isNotEmpty()) confirmPasswordError = ""
                },
                placeholder = Translations.t("confirm_password_placeholder"),
                textColor = Color.Black,
                containerColor = Color.White,
                isPassword = true
            )
            if (confirmPasswordError.isNotEmpty()) ValidationMessage(message = confirmPasswordError)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    triedToSubmit = true
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

                    if (password.isBlank()) {
                        passwordError = Translations.t("error_password_required")
                        hasError = true
                    } else if (!ValidatorUtils.isSecurePassword(password)) {
                        passwordError = Translations.t("error_password_security")
                        hasError = true
                    }

                    if (confirmPassword.isBlank()) {
                        confirmPasswordError = Translations.t("confirm_password_required")
                        hasError = true
                    } else if (password != confirmPassword) {
                        confirmPasswordError = Translations.t("error_password_mismatch")
                        hasError = true
                    }

                    if (!hasError) {
                        val newUser = User(
                            id = UUID.randomUUID().toString(),
                            name = name,
                            lastName = lastname,
                            email = email,
                            password = password,
                            imageProfile = "",
                            rol = "usuario"
                        )

                        val success = userSessionViewModel.registerUser(newUser)

                        if (success) {
                            Toast.makeText(context, Translations.t("account_created_success"), Toast.LENGTH_SHORT).show()
                            navController.navigate(Screens.Login.route)
                        } else {
                            Toast.makeText(context, Translations.t("email_in_use"), Toast.LENGTH_SHORT).show()
                        }
                    }

                },
                modifier = Modifier
                    .width(300.dp)
                    .height(56.dp)
                    .shadow(8.dp, RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF06BB0C))
            ) {
                Text(Translations.t("register"), color = Color.White, fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            val googleSignInClient = getGoogleSignInClient(context)
            val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account = task.result
                    val email = account?.email ?: ""
                    val name = account?.givenName ?: ""
                    val lastName = account?.familyName ?: ""
                    val imageProfile = account?.photoUrl?.toString() ?: ""

                    val newUser = User(
                        id = account?.id ?: UUID.randomUUID().toString(),
                        name = name,
                        lastName = lastName,
                        email = email,
                        password = "",
                        imageProfile = imageProfile,
                        rol = "usuario"
                    )

                    val existingUser = userSessionViewModel.getUserByEmail(email)
                    if (existingUser == null) {
                        userSessionViewModel.registerUser(newUser)
                        Toast.makeText(context, Translations.t("google_register_success"), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, Translations.t("account_exists"), Toast.LENGTH_SHORT).show()
                    }
                    navController.navigate(Screens.Login.route)
                } else {
                    Toast.makeText(context, Translations.t("google_register_error"), Toast.LENGTH_SHORT).show()
                }
            }
            Button(
                onClick = { launcher.launch(googleSignInClient.signInIntent) },
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
                        text = Translations.t("register_with_google"),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

            }
            Spacer(modifier = Modifier.height(24.dp))


            Row {
                Text(text = Translations.t("already_have_account"),color = Color.White,
                    fontSize = 16.sp,fontFamily = montserratFontFamily)
                Text(
                    text = Translations.t("login"),
                    color = Color(0xFF0084FF),
                    fontSize = 16.sp,
                    fontFamily = montserratFontFamily,
                    modifier = Modifier.clickable {
                        navController.navigate(Screens.Login.route)
                    },
                    fontWeight = FontWeight.Bold
                )
            }


        }
    }
}