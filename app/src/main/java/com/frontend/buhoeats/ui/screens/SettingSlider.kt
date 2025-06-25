package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.models.User
import com.frontend.buhoeats.navigation.Screens
import com.frontend.buhoeats.ui.theme.AppColors
import com.frontend.buhoeats.ui.theme.ThemeManager

@Composable
fun SettingSlider(
    onNavigateToProfile: () -> Unit = {},
    navController: NavController,
    currentUser: User,
    restaurant: Restaurant? = null,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(AppColors.fondo)) {

        val backgroundImage = if (ThemeManager.isDarkTheme)
            painterResource(R.drawable.backgrounddark)
        else
            painterResource(R.drawable.backgroundlighttheme)

        Image(
            painter = backgroundImage,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )


        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(15.dp)
                    .clickable { onNavigateToProfile() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = "Mi cuenta",
                    tint = AppColors.texto,
                    modifier = Modifier.size(45.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("Mi cuenta", fontSize = 25.sp, color = AppColors.texto)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Cuenta",
                    tint = AppColors.texto,
                    modifier = Modifier
                        .graphicsLayer { rotationY = 180f }
                        .padding(10.dp)
                        .size(30.dp)
                )
            }

            var expandedTheme by remember { mutableStateOf(false) }
            var selectedTheme by remember { mutableStateOf(if (ThemeManager.isDarkTheme) "Oscuro" else "Claro") }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .clickable { expandedTheme = !expandedTheme }
                ) {
                    val themeIcon = if (selectedTheme == "Claro") Icons.Outlined.WbSunny else Icons.Outlined.DarkMode

                    Icon(
                        imageVector = themeIcon,
                        contentDescription = "Modo",
                        tint = AppColors.texto,
                        modifier = Modifier.size(45.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Modo $selectedTheme", fontSize = 25.sp, color = AppColors.texto)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Expandir tema",
                        tint = AppColors.texto,
                        modifier = Modifier
                            .graphicsLayer { rotationZ = -90f }
                            .padding(10.dp)
                            .size(30.dp)
                    )
                }

                DropdownMenu(
                    expanded = expandedTheme,
                    onDismissRequest = { expandedTheme = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.WbSunny, "Claro", Modifier.size(30.dp))
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Claro", fontSize = 22.sp, color = AppColors.texto)
                                Spacer(modifier = Modifier.weight(1f))
                                RadioButton(selected = selectedTheme == "Claro", onClick = null)
                            }
                        },
                        onClick = {
                            selectedTheme = "Claro"
                            ThemeManager.toggleTheme(false)
                            expandedTheme = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.DarkMode, "Oscuro", Modifier.size(30.dp))
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Oscuro", fontSize = 22.sp, color = AppColors.texto)
                                Spacer(modifier = Modifier.weight(1f))
                                RadioButton(
                                    selected = selectedTheme == "Oscuro",
                                    onClick = null,
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = AppColors.secondary,
                                        unselectedColor = AppColors.texto,
                                        disabledSelectedColor = AppColors.secondary,
                                        disabledUnselectedColor = AppColors.texto
                                    )
                                )
                            }
                        },
                        onClick = {
                            selectedTheme = "Oscuro"
                            ThemeManager.toggleTheme(true)
                            expandedTheme = false
                        }
                    )
                }
            }

            // Idioma
            var expandedLang by remember { mutableStateOf(false) }
            var selectedLang by remember { mutableStateOf("Español") }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .clickable { expandedLang = true }
                ) {
                    Icon(Icons.Outlined.GTranslate, "Idioma", tint = AppColors.texto, modifier = Modifier.size(45.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Idioma", fontSize = 25.sp, color = AppColors.texto)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.ArrowBackIosNew, "Expandir idioma", tint = AppColors.texto,
                        modifier = Modifier.graphicsLayer { rotationZ = -90f }.padding(10.dp).size(30.dp))
                }

                DropdownMenu(
                    expanded = expandedLang,
                    onDismissRequest = { expandedLang = false },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)
                ) {
                    listOf("Español", "Inglés").forEach { lang ->
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Spacer(modifier = Modifier.size(45.dp))
                                    Text(lang, fontSize = 22.sp, color = AppColors.texto)
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(selected = selectedLang == lang, onClick = null,
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = AppColors.secondary,
                                            unselectedColor = AppColors.texto,
                                            disabledSelectedColor = AppColors.secondary,
                                            disabledUnselectedColor = AppColors.texto
                                        )
                                        )
                                }
                            },
                            onClick = {
                                selectedLang = lang
                                expandedLang = false
                            }
                        )
                    }
                }
            }

            // Opciones según rol
            if (currentUser.rol == "usuario") {
                SettingItem("Favoritos", Icons.Outlined.FavoriteBorder) {
                    navController.navigate(Screens.Favorites.route)
                }
            }

            if (currentUser.rol == "admin" && restaurant != null) {
                SettingItem("Ver estadísticas", Icons.Outlined.BarChart) {
                    navController.navigate(Screens.Statistics.route)
                }
                SettingItem("Clientes bloqueados", Icons.Outlined.Block) {
                    navController.navigate(Screens.BlockedUser.createRoute(restaurant.id.toString()))
                }
            }

            if (currentUser.rol == "superadmin") {
                SettingItem("Asignar roles a un usuario", Icons.Outlined.SupervisorAccount) {
                    navController.navigate(Screens.RolAssign.route)
                }
            }
        }
    }
}

@Composable
fun SettingItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(15.dp).clickable { onClick() }
    ) {
        Icon(icon, contentDescription = text, tint = AppColors.texto, modifier = Modifier.size(45.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text, fontSize = 25.sp, color = AppColors.texto, maxLines = 2, overflow = TextOverflow.Ellipsis)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            Icons.Default.ArrowBackIosNew,
            contentDescription = text,
            tint = AppColors.texto,
            modifier = Modifier
                .graphicsLayer { rotationY = 180f }
                .padding(10.dp)
                .size(30.dp)
        )
    }
}
