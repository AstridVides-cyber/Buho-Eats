package com.frontend.buhoeats.ui.screens

import android.util.Log
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.frontend.buhoeats.auth.getGoogleSignInClient
import com.frontend.buhoeats.models.User
import com.frontend.buhoeats.navigation.Screens
import com.frontend.buhoeats.ui.components.CustomTextField
import com.frontend.buhoeats.utils.ValidatorUtils
import com.frontend.buhoeats.ui.components.ValidationMessage
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
        color = Color(0xFF3D405B)
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
                    text = "Crear cuenta",
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
                label = "Nombre",
                value = name,
                onValueChange = {
                    name = ValidatorUtils.capitalizeWords(it)
                    if (nameError.isNotEmpty()) nameError = ""
                },
                placeholder = "Ingrese su nombre",
                textColor = Color.Black,
                containerColor = Color.White
            )
            if (nameError.isNotEmpty()) ValidationMessage(message = nameError)

            Spacer(modifier = Modifier.height(12.dp))

            CustomTextField(
                label = "Apellido",
                value = lastname,
                onValueChange = {
                    lastname = ValidatorUtils.capitalizeWords(it)
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
                    } else if (!ValidatorUtils.isSecurePassword(password)) {
                        passwordError = "La contraseña debe tener al menos 8 caracteres, una mayúscula, un número y un símbolo"
                        hasError = true
                    }

                    if (confirmPassword.isBlank()) {
                        confirmPasswordError = "Debe confirmar la contraseña"
                        hasError = true
                    } else if (password != confirmPassword) {
                        confirmPasswordError = "Las contraseñas no coinciden"
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
                            Toast.makeText(context, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()
                            navController.navigate(Screens.Login.route)
                        } else {
                            Toast.makeText(context, "Este correo ya está en uso", Toast.LENGTH_SHORT).show()
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
                Text("Registrarte", color = Color.White, fontSize = 20.sp)
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
                        Toast.makeText(context, "Cuenta Google registrada con éxito", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Ya existe una cuenta con este correo", Toast.LENGTH_SHORT).show()
                    }
                    navController.navigate(Screens.Login.route)
                } else {
                    Toast.makeText(context, "Error al iniciar sesión con Google", Toast.LENGTH_SHORT).show()
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
                        text = "Registrate con Google",
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
                        navController.navigate(Screens.Login.route)
                    },
                    fontWeight = FontWeight.Bold
                )
            }


        }
    }
}