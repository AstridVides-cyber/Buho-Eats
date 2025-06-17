package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar
import androidx.annotation.DrawableRes



data class RoleOption(val label: String, @DrawableRes val imageRes: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RolAssign(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }

    val roleOptions = listOf(
        RoleOption("Super Administrador", R.drawable.super_admin),
        RoleOption("Administrador de Local", R.drawable.admi_local),
        RoleOption("Usuario", R.drawable.ususario)
    )
    var selectedRole by remember { mutableStateOf<RoleOption?>(null) }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                showBackIcon = true,
                onNavClick = { navController.popBackStack() }
            )
        },
        bottomBar = { BottomNavigationBar(navController) },
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
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Asignar Roles",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFontFamily,
                    modifier = Modifier
                        .padding(top = 50.dp, bottom = 20.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    "Correo:",
                    color = Color.Black,
                    fontSize = 18.sp,
                    style = TextStyle(fontFamily = montserratFontFamily),
                    modifier = Modifier
                        .padding(start = 25.dp, bottom = 8.dp)
                        .align(Alignment.Start)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        if (emailError.isNotEmpty()) emailError = ""
                    },
                    placeholder = {
                        Text(
                            "Ingrese el correo del usuario",
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Black,
                        unfocusedBorderColor = Color.Black,
                        disabledBorderColor = Color.Black,
                        errorBorderColor = Color.Red,
                        focusedContainerColor = Color(0xFFF3EDED),
                        unfocusedContainerColor = Color(0xFFF3EDED),
                        disabledContainerColor = Color(0xFFF3EDED),
                        errorContainerColor = Color(0xFFF3EDED),
                        cursorColor = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    "Tipo de Rol:",
                    color = Color.Black,
                    fontSize = 18.sp,
                    style = TextStyle(fontFamily = montserratFontFamily),
                    modifier = Modifier
                        .padding(start = 25.dp, bottom = 8.dp)
                        .align(Alignment.Start)
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    OutlinedTextField(
                        value = selectedRole?.label ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text("Seleccione un rol", fontWeight = FontWeight.SemiBold, fontFamily = montserratFontFamily)
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFF3EDED),
                            unfocusedContainerColor = Color(0xFFF3EDED),
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            cursorColor = Color.Black
                        ),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = montserratFontFamily,
                            color = Color.Black
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        roleOptions.forEach { role ->
                            DropdownMenuItem(
                                text = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Image(
                                            painter = painterResource(id = role.imageRes),
                                            contentDescription = null,
                                            modifier = Modifier.size(24.dp)
                                        )
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Text(
                                            text = role.label,
                                            fontSize = 16.sp,
                                            fontFamily = montserratFontFamily,
                                            color = Color.Black
                                        )
                                    }
                                },
                                onClick = {
                                    selectedRole = role
                                    expanded = false
                                }
                            )

                        }
                    }
                }
            }
        }
    }
}
