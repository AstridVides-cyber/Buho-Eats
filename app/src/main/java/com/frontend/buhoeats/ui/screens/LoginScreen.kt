package com.frontend.buhoeats.ui.screens


import android.os.Build
import android.widget.Toast
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.navigation.Screens
import com.frontend.buhoeats.utils.ValidatorUtils.isValidEmail
import com.frontend.buhoeats.ui.components.ValidationMessage
import com.frontend.buhoeats.data.InMemoryUserDataSource
import com.frontend.buhoeats.viewmodel.UserSessionViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.frontend.buhoeats.auth.getGoogleSignInClient
import com.frontend.buhoeats.models.User
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Login(
    navControl: NavHostController,
    userSessionViewModel: UserSessionViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val googleSignInClient = remember { getGoogleSignInClient(context) }
    val firebaseAuth = remember { FirebaseAuth.getInstance() }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.result
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener { authResult ->
                    if (authResult.isSuccessful) {
                        val firebaseUser = firebaseAuth.currentUser
                        firebaseUser?.let { user ->
                            val displayName = user.displayName ?: ""
                            val (name, lastName) = run {
                                val parts = displayName.split(" ")
                                Pair(parts.firstOrNull() ?: "", parts.drop(1).joinToString(" "))
                            }

                            val newUser = User(
                                id = user.uid,
                                name = name,
                                lastName = lastName,
                                email = user.email ?: "",
                                password = "",
                                imageProfile = user.photoUrl?.toString() ?: "",
                                rol = "usuario"
                            )

                            if (userSessionViewModel.getUserByEmail(newUser.email) == null) {
                                userSessionViewModel.registerUser(newUser)
                            }

                            userSessionViewModel.login(newUser)

                            navControl.navigate(Screens.Home.route) {
                                popUpTo(Screens.Login.route) { inclusive = true }
                            }
                        }

                        navControl.navigate(Screens.Home.route) {
                            popUpTo(Screens.Login.route) { inclusive = true }
                        }
                    } else {
                        Toast.makeText(context, "Error al iniciar sesión con Google", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(context, "Error al autenticar con Google", Toast.LENGTH_SHORT).show()
        }
    }


    val containerColor = Color.White

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
                        isLoading = true

                        CoroutineScope(Dispatchers.Main).launch {
                            delay(2000)

                            val user = InMemoryUserDataSource.getUsers().find {
                                it.email == email && it.password == password
                            }
                            isLoading = false

                            if (user != null) {
                                userSessionViewModel.login(user)
                                Toast.makeText(context, "Sesión iniciada con éxito", Toast.LENGTH_SHORT).show()
                                navControl.navigate(Screens.Home.route) {
                                    popUpTo(Screens.Login.route) { inclusive = true }
                                }
                            } else {
                                passwordError = "Correo o contraseña incorrectos"
                            }
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

                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                    Spacer(modifier = Modifier.height(24.dp))
                }

                Button(
                onClick = { val signInIntent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent) },
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
