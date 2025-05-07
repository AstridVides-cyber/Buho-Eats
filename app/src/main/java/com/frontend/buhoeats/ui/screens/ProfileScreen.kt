package com.frontend.buhoeats.ui.screens

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frontend.buhoeats.R
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.graphicsLayer
import coil.compose.rememberAsyncImagePainter
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.ConfirmationDialog
import com.frontend.buhoeats.ui.components.ProfileTextField
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.ui.components.ValidationMessage
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen() {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> selectedImageUri = uri }

    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var apellido by remember { mutableStateOf(TextFieldValue("")) }
    var correo by remember { mutableStateOf(TextFieldValue("")) }

    var nombreError by remember { mutableStateOf("") }
    var apellidoError by remember { mutableStateOf("") }
    var correoError by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    var showLogoutDialog by remember { mutableStateOf(false) }
    if (showLogoutDialog) {
        ConfirmationDialog(
            message = "¿Estás seguro que deseas cerrar sesión?",
            onConfirm = {
                showLogoutDialog = false
                // logica futura
            },
            onDismiss = { showLogoutDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopBar()
        },
        bottomBar = {
            Column {
                Button(
                    onClick = {
                         showLogoutDialog = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF588B8B)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Icon(Icons.Filled.Logout, contentDescription = null, tint = Color.White, modifier = Modifier.size(35.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Cerrar Sesión", color = Color.White, fontSize = 25.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "back",
                        modifier = Modifier
                            .graphicsLayer { rotationZ = 180f }
                            .size(32.dp)
                    )
                }
                BottomNavigationBar()
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.backgroundlighttheme),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 40.dp, vertical = 20.dp)
                    .padding(bottom = 160.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Mi cuenta",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )

                Image(
                    painter = if (selectedImageUri != null)
                        rememberAsyncImagePainter(model = selectedImageUri)
                    else
                        painterResource(id = R.drawable.defaulticon),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(175.dp)
                        .clickable { launcher.launch("image/*") },
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                ProfileTextField("Nombre:", nombre, onChange = {
                    nombre = it
                    if (nombreError.isNotEmpty()) nombreError = ""
                })
                if (nombreError.isNotEmpty()) {
                    ValidationMessage(message = nombreError)
                }

                Spacer(modifier = Modifier.height(10.dp))

                ProfileTextField("Apellido:", apellido, onChange = {
                    apellido = it
                    if (apellidoError.isNotEmpty()) apellidoError = ""
                })
                if (apellidoError.isNotEmpty()) {
                    ValidationMessage(message = apellidoError)
                }

                Spacer(modifier = Modifier.height(10.dp))

                ProfileTextField("Correo:", correo, onChange = {
                    correo = it
                    if (correoError.isNotEmpty()) correoError = ""
                }, isEmail = true)
                if (correoError.isNotEmpty()) {
                    ValidationMessage(message = correoError)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        var hasError = false
                        if (nombre.text.isBlank()) {
                            nombreError = "El nombre no puede estar vacío"
                            hasError = true
                        }
                        if (apellido.text.isBlank()) {
                            apellidoError = "El apellido no puede estar vacío"
                            hasError = true
                        }
                        if (correo.text.isBlank()) {
                            correoError = "El correo no puede estar vacío"
                            hasError = true
                        }
                        if (!hasError) {
                            // Guardar perfil
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC11D0C)),
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(55.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Editar perfil", color = Color.White, fontSize = 17.sp)
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    ProfileScreen()
}
