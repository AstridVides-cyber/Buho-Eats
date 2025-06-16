package com.frontend.buhoeats.ui.screens

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.frontend.buhoeats.R
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.FormField
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.ui.components.ValidationMessage

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditLocalScreen(
    isNewLocal: Boolean,
    navController: NavController,
    adminEmail: String = "admin@email.com", // reemplazar con dato real
    onBackClick: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf(false) }
    var descriptionError by remember { mutableStateOf(false) }

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

    Scaffold(
        topBar = {
            TopBar(showBackIcon = true, onNavClick = onBackClick)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* confirmación pendiente */ },
                containerColor = Color(0xFF06BB0C),
                contentColor = Color.White,
                modifier = Modifier.size(70.dp),
                shape = CircleShape
            ) {
                Icon(Icons.Default.Check, contentDescription = "Confirmar", modifier = Modifier.size(45.dp))
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    text = if (isNewLocal) "Agregar local" else "Editar local",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Cuadro de imagen
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    if (selectedImageUri != null) {
                        AsyncImage(
                            model = selectedImageUri,
                            contentDescription = "Imagen seleccionada",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.LightGray)
                        )
                    }

                    IconButton(
                        onClick = { imagePickerLauncher.launch("image/*") },
                        modifier = Modifier
                            .offset(x = (-8).dp, y = (-8).dp)
                            .size(48.dp)
                            .background(Color.White, CircleShape)
                            .border(1.dp, Color.Gray, CircleShape)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = "Cambiar imagen"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Campos
                FormField(
                    label = "Nombre:",
                    value = name,
                    onValueChange = { name = it; nameError = false },
                    isError = nameError,
                    placeholderText = "Ej. Pupusería La Esperanza"
                )
                if (nameError) ValidationMessage("El nombre no puede estar vacío")

                Spacer(modifier = Modifier.height(16.dp))

                FormField(
                    label = "Descripción:",
                    value = description,
                    onValueChange = { description = it; descriptionError = false },
                    isError = descriptionError,
                    isMultiline = true,
                    placeholderText = "Breve descripción del local"
                )
                if (descriptionError) ValidationMessage("La descripción no puede estar vacía")

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = adminEmail,
                    onValueChange = {},
                    enabled = false,
                    label = { Text("Administrador:") },
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = TextStyle(fontSize = 16.sp)
                )
            }
        }
    }
}
