package com.frontend.buhoeats.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frontend.buhoeats.R
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.ConfirmationDialog
import com.frontend.buhoeats.ui.components.DisabledProfileField
import com.frontend.buhoeats.ui.components.TopBar
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    onNavigateToAccount: () -> Unit = {},
    onBack: () -> Unit = {},
    navController: NavController)
{
    val scrollState = rememberScrollState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        ConfirmationDialog(
            message = "¿Estás seguro que deseas cerrar sesión?",
            onConfirm = { showLogoutDialog = false },
            onDismiss = { showLogoutDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopBar(
                showBackIcon = true,
                onNavClick = onBack
            )
        },
        bottomBar = {
            Column {
                Button(
                    onClick = { showLogoutDialog = true },
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
                        modifier = Modifier.graphicsLayer { rotationZ = 180f }.size(32.dp)
                    )
                }
                BottomNavigationBar(navController)
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
                    painter = painterResource(id = R.drawable.defaulticon),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                            .size(180.dp)
                            .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                DisabledProfileField("Nombre:", "Michelle")
                Spacer(modifier = Modifier.height(10.dp))
                DisabledProfileField("Apellido:", "Maltez")
                Spacer(modifier = Modifier.height(10.dp))
                DisabledProfileField("Correo:", "michelle@example.com")

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { onNavigateToAccount()
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