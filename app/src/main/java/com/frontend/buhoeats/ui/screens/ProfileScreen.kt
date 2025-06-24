package com.frontend.buhoeats.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frontend.buhoeats.R
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.graphicsLayer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.navigation.Screens
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.ConfirmationDialog
import com.frontend.buhoeats.ui.components.DisabledProfileField
import com.frontend.buhoeats.ui.components.StaticProfileImage
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.ui.theme.AppColors
import com.frontend.buhoeats.ui.theme.ThemeManager
import com.frontend.buhoeats.viewmodel.UserSessionViewModel
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProfileScreen(
    onNavigateToAccount: () -> Unit = {},
    onBack: () -> Unit = {},
    navController: NavController,
    userSessionViewModel: UserSessionViewModel
) {
    val scrollState = rememberScrollState()
    val user = userSessionViewModel.currentUser.value
    var showLogoutDialog by remember { mutableStateOf(false) }

    val backgroundImage = if (ThemeManager.isDarkTheme)
        painterResource(id = R.drawable.backgrounddark)
    else
        painterResource(id = R.drawable.backgroundlighttheme)

    if (user == null) {
        LaunchedEffect(Unit) {
            navController.navigate(Screens.Login.route) {
                popUpTo(Screens.Profile.route) { inclusive = true }
            }
        }
        return
    }

    if (showLogoutDialog) {
        ConfirmationDialog(
            message = "¿Estás seguro que deseas cerrar sesión?",
            onConfirm = {
                showLogoutDialog = false
                userSessionViewModel.logout()
                navController.navigate(Screens.Login.route) {
                    popUpTo(Screens.Profile.route) { inclusive = true }
                }
            },
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
                    colors = ButtonDefaults.buttonColors(containerColor = AppColors.secondary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(75.dp),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Icon(Icons.Filled.Logout, contentDescription = null, tint = AppColors.text, modifier = Modifier.size(35.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text("Cerrar Sesión", color = AppColors.text, fontSize = 25.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "back",
                        modifier = Modifier.graphicsLayer { rotationZ = 180f }.size(32.dp),
                        tint = AppColors.text
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
                painter = backgroundImage,
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

                StaticProfileImage(user.imageProfile)

                Spacer(modifier = Modifier.height(16.dp))

                DisabledProfileField("Nombre:", user.name)
                Spacer(modifier = Modifier.height(10.dp))
                DisabledProfileField("Apellido:", user.lastName)
                Spacer(modifier = Modifier.height(10.dp))
                DisabledProfileField("Correo:", user.email)

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onNavigateToAccount,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC11D0C)),
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(55.dp),
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                ) {
                    Text("Editar perfil", color = Color.White, fontSize = 17.sp)
                }
            }
        }
    }
}
