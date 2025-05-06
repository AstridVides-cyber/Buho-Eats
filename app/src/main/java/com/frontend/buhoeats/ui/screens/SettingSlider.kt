package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.GTranslate
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frontend.buhoeats.R
import com.frontend.buhoeats.ui.components.BottomNavigationBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingSlider(onBack: () -> Unit = {}) {

    Scaffold(
        topBar = {
                //top bar
        },
        bottomBar = {
            BottomNavigationBar()
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

            Column {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = "Mi cuenta",
                        tint = Color.Black,
                        modifier = Modifier.size(45.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Mi cuenta", fontSize = 25.sp, color = Color.Black)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Cuenta",
                        modifier = Modifier
                            .graphicsLayer { rotationY = 180f }
                            .padding(10.dp)
                            .size(30.dp)
                    )
                }

                var expandedTheme by remember { mutableStateOf(false) }
                var selectedTheme by remember { mutableStateOf("Claro") }

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
                            tint = Color.Black,
                            modifier = Modifier.size(45.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Modo $selectedTheme", fontSize = 25.sp, color = Color.Black)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Expandir tema",
                            tint = Color.Black,
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
                                    Icon(
                                        imageVector = Icons.Outlined.WbSunny,
                                        contentDescription = "sol",
                                        modifier = Modifier.size(30.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text("Claro", fontSize = 22.sp)
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(selected = selectedTheme == "Claro", onClick = null)
                                }
                            },
                            onClick = {
                                selectedTheme = "Claro"
                                expandedTheme = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Outlined.DarkMode,
                                        contentDescription = "luna",
                                        modifier = Modifier.size(30.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text("Oscuro", fontSize = 22.sp)
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(selected = selectedTheme == "Oscuro", onClick = null)
                                }
                            },
                            onClick = {
                                selectedTheme = "Oscuro"
                                expandedTheme = false
                            }
                        )
                    }
                }

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
                        Icon(
                            imageVector = Icons.Outlined.GTranslate,
                            contentDescription = "Idioma",
                            tint = Color.Black,
                            modifier = Modifier.size(45.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Idioma", fontSize = 25.sp, color = Color.Black)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Expandir idioma",
                            tint = Color.Black,
                            modifier = Modifier
                                .graphicsLayer { rotationZ = -90f }
                                .padding(10.dp)
                                .size(30.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = expandedLang,
                        onDismissRequest = { expandedLang = false },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Spacer(modifier = Modifier.size(45.dp))
                                    Text("Español", fontSize = 22.sp)
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(
                                        selected = selectedLang == "Español",
                                        onClick = null
                                    )
                                }
                            },
                            onClick = {
                                selectedLang = "Español"
                                expandedLang = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Spacer(modifier = Modifier.size(45.dp))
                                    Text("Inglés", fontSize = 22.sp)
                                    Spacer(modifier = Modifier.weight(1f))
                                    RadioButton(
                                        selected = selectedLang == "Inglés",
                                        onClick = null
                                    )
                                }
                            },
                            onClick = {
                                selectedLang = "Inglés"
                                expandedLang = false
                            }
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favoritos",
                        tint = Color.Black,
                        modifier = Modifier.size(45.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Favoritos", fontSize = 25.sp, color = Color.Black)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Cuenta",
                        modifier = Modifier
                            .graphicsLayer { rotationY = 180f }
                            .padding(10.dp)
                            .size(30.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SliderPreview () {
    SettingSlider()
}
