package com.frontend.buhoeats.ui.screens

import android.graphics.Color.rgb
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GTranslate
import androidx.compose.material.icons.filled.NightsStay
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.WbSunny
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingSlider(onBack: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.backgroundlighttheme),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logoletra),
                                contentDescription = null,
                                modifier = Modifier.size(120.dp), // más grande
                                contentScale = ContentScale.Fit
                            )
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .height(130.dp) // ¡búho grande!
                                    .width(40.dp)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack,
                        modifier = Modifier.height(80.dp)) {
                        Icon(
                            Icons.Default.ArrowBackIosNew,
                            contentDescription = "Volver",
                            modifier = Modifier.size(35.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(rgb(61, 64, 91)),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                ),
                modifier = Modifier.height(80.dp)
            )



            Spacer(modifier = Modifier.height(24.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Person, contentDescription = "Mi cuenta", tint = Color.Black)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Mi cuenta", fontSize = 18.sp, color = Color.Black)
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Cuenta",
                        modifier = Modifier.graphicsLayer {
                            rotationY = 180f
                        }
                            .padding(10.dp)
                    )


            }

            var expandedTheme by remember { mutableStateOf(false) }
            var selectedTheme by remember { mutableStateOf("Claro") }

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .clickable { expandedTheme = !expandedTheme }
                ) {
                    val themeIcon = if (selectedTheme == "Claro") Icons.Default.WbSunny else Icons.Default.NightsStay

                    Icon(themeIcon, contentDescription = "Modo", tint = Color.Black)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Modo $selectedTheme", fontSize = 16.sp, color = Color.Black)
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Expandir tema",
                        tint = Color.Black,
                        modifier = Modifier
                            .graphicsLayer { rotationZ = -90f }
                            .padding(10.dp)
                    )
                }

                DropdownMenu(
                    expanded = expandedTheme,
                    onDismissRequest = { expandedTheme = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.WbSunny, contentDescription = "sol")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Claro")
                                Spacer(modifier = Modifier.width(8.dp))
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
                                Icon(imageVector = Icons.Default.NightsStay, contentDescription = "luna")
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Oscuro")
                                Spacer(modifier = Modifier.width(8.dp))
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
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth()
                        .clickable { expandedLang = true }
                ) {
                    Icon(Icons.Default.GTranslate, contentDescription = "Idioma", tint = Color.Black)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text("Idioma", fontSize = 16.sp, color = Color.Black)
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Expandir tema",
                        tint = Color.Black,
                        modifier = Modifier
                            .graphicsLayer { rotationZ = -90f }
                            .padding(10.dp)
                    )

                }

                DropdownMenu(
                    expanded = expandedLang,
                    onDismissRequest = { expandedLang = false },
                    modifier = Modifier.fillMaxWidth(0.9f)
                ) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Español")
                                Spacer(modifier = Modifier.width(8.dp))
                                RadioButton(
                                    selected = selectedLang == "Español",
                                    onClick = null // deshabilitado aquí, manejado por onClick del item
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
                                Text("Inglés")
                                Spacer(modifier = Modifier.width(8.dp))
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
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Favorite, contentDescription = "Favoritos", tint = Color.Black)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Favoritos", fontSize = 18.sp, color = Color.Black)
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Cuenta",
                    modifier = Modifier.graphicsLayer {
                        rotationY = 180f
                    }
                        .padding(10.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun SliderPreview () {
    SettingSlider()
}
