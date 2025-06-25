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
import com.frontend.buhoeats.utils.Translations
import com.frontend.buhoeats.utils.Translations.Language

@Composable
fun SettingSlider(
    onNavigateToProfile: () -> Unit = {},
    navController: NavController,
    currentUser: User,
    restaurant: Restaurant? = null,
) {
    Box(modifier = Modifier.fillMaxSize().background(AppColors.fondo)) {

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

            // Cuenta
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(15.dp)
                    .clickable { onNavigateToProfile() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    contentDescription = Translations.t("my_account"),
                    tint = AppColors.texto,
                    modifier = Modifier.size(45.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(Translations.t("my_account"), fontSize = 25.sp, color = AppColors.texto)
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = Translations.t("my_account"),
                    modifier = Modifier
                        .graphicsLayer { rotationY = 180f }
                        .padding(10.dp)
                        .size(30.dp)
                )
            }

            // Modo
            var expandedTheme by remember { mutableStateOf(false) }
            var selectedTheme by remember {
                mutableStateOf(
                    if (ThemeManager.isDarkTheme) Translations.t("dark")
                    else Translations.t("light")
                )
            }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .clickable { expandedTheme = !expandedTheme }
                ) {
                    val themeIcon =
                        if (selectedTheme == Translations.t("light")) Icons.Outlined.WbSunny
                        else Icons.Outlined.DarkMode

                    Icon(
                        imageVector = themeIcon,
                        contentDescription = Translations.t("mode"),
                        tint = AppColors.texto,
                        modifier = Modifier.size(45.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("${Translations.t("mode")} $selectedTheme", fontSize = 25.sp, color = AppColors.texto)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = Translations.t("mode"),
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
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.WbSunny, contentDescription = null, modifier = Modifier.size(30.dp))
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(Translations.t("light"), fontSize = 22.sp, color = AppColors.texto)
                                Spacer(modifier = Modifier.weight(1f))
                                RadioButton(selected = selectedTheme == Translations.t("light"), onClick = null)
                            }
                        },
                        onClick = {
                            selectedTheme = Translations.t("light")
                            ThemeManager.toggleTheme(false)
                            expandedTheme = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.DarkMode, contentDescription = null, modifier = Modifier.size(30.dp))
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(Translations.t("dark"), fontSize = 22.sp, color = AppColors.texto)
                                Spacer(modifier = Modifier.weight(1f))
                                RadioButton(
                                    selected = selectedTheme == Translations.t("dark"),
                                    onClick = null,
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = AppColors.secondary,
                                        unselectedColor = AppColors.texto
                                    )
                                )
                            }
                        },
                        onClick = {
                            selectedTheme = Translations.t("dark")
                            ThemeManager.toggleTheme(true)
                            expandedTheme = false
                        }
                    )
                }
            }

            // Idioma
            var expandedLang by remember { mutableStateOf(false) }
            var selectedLang by remember {
                mutableStateOf(
                    if (Translations.currentLanguage == Language.ES) "Español" else "Inglés"
                )
            }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth()
                        .clickable { expandedLang = true }
                ) {
                    Icon(Icons.Outlined.GTranslate, contentDescription = Translations.t("language"), tint = AppColors.texto, modifier = Modifier.size(45.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(Translations.t("language"), fontSize = 25.sp, color = AppColors.texto)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        Icons.Default.ArrowBackIosNew,
                        contentDescription = Translations.t("language"),
                        tint = AppColors.texto,
                        modifier = Modifier.graphicsLayer { rotationZ = -90f }.padding(10.dp).size(30.dp)
                    )
                }

                DropdownMenu(
                    expanded = expandedLang,
                    onDismissRequest = { expandedLang = false },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 15.dp)
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Spacer(modifier = Modifier.size(45.dp))
                                Text("Español", fontSize = 22.sp, color = AppColors.texto)
                                Spacer(modifier = Modifier.weight(1f))
                                RadioButton(selected = selectedLang == "Español", onClick = null)
                            }
                        },
                        onClick = {
                            selectedLang = "Español"
                            Translations.currentLanguage = Language.ES
                            expandedLang = false
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Spacer(modifier = Modifier.size(45.dp))
                                Text("Inglés", fontSize = 22.sp, color = AppColors.texto)
                                Spacer(modifier = Modifier.weight(1f))
                                RadioButton(selected = selectedLang == "Inglés", onClick = null)
                            }
                        },
                        onClick = {
                            selectedLang = "Inglés"
                            Translations.currentLanguage = Language.EN
                            expandedLang = false
                        }
                    )
                }
            }

            if (currentUser.rol == "usuario") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(15.dp).clickable {
                        navController.navigate(Screens.Favorites.route)
                    }
                ) {
                    Icon(Icons.Outlined.FavoriteBorder, contentDescription = Translations.t("favorites"), tint = AppColors.texto, modifier = Modifier.size(45.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(Translations.t("favorites"), fontSize = 25.sp, color = AppColors.texto)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = null, tint = AppColors.texto, modifier = Modifier.graphicsLayer { rotationY = 180f }.padding(10.dp).size(30.dp))
                }
            }

            if (currentUser.rol == "admin" && restaurant != null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(15.dp).clickable {
                        navController.navigate(Screens.Statistics.route)
                    }
                ) {
                    Icon(Icons.Outlined.BarChart, contentDescription = Translations.t("statistics"), tint = AppColors.texto, modifier = Modifier.size(45.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(Translations.t("statistics"), fontSize = 25.sp, color = AppColors.texto)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = null, tint = AppColors.texto, modifier = Modifier.graphicsLayer { rotationY = 180f }.padding(10.dp).size(30.dp))
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(15.dp).clickable {
                        navController.navigate(Screens.BlockedUser.createRoute(restaurant.id.toString()))
                    }
                ) {
                    Icon(Icons.Outlined.Block, contentDescription = Translations.t("blocked_users"), tint = AppColors.texto, modifier = Modifier.size(45.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(Translations.t("blocked_users"), fontSize = 25.sp, color = AppColors.texto)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = null, tint = AppColors.texto, modifier = Modifier.graphicsLayer { rotationY = 180f }.padding(10.dp).size(30.dp))
                }
            }

            if (currentUser.rol == "superadmin") {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(15.dp).clickable {
                        navController.navigate(Screens.RolAssign.route)
                    }
                ) {
                    Icon(Icons.Outlined.SupervisorAccount, contentDescription = Translations.t("assign_roles"), tint = AppColors.texto, modifier = Modifier.size(45.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = Translations.t("assign_roles"),
                            fontSize = 25.sp,
                            color = AppColors.texto,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Icon(Icons.Default.ArrowBackIosNew, contentDescription = null, tint = AppColors.texto, modifier = Modifier.graphicsLayer { rotationY = 180f }.padding(10.dp).size(30.dp))
                }
            }
        }
    }
}
