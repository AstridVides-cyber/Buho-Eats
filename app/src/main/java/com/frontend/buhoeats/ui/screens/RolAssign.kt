package com.frontend.buhoeats.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar

data class RoleOption(val label: String, @DrawableRes val imageRes: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RolAssign(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }

    var textFieldSize by remember { mutableStateOf(IntSize.Zero) }

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
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = selectedRole?.label ?: "",
                        onValueChange = {},
                        placeholder = {
                            Text(
                                "Seleccione un rol",
                                fontFamily = montserratFontFamily,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
                            )
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                textFieldSize = coordinates.size
                            },
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Black,
                            unfocusedBorderColor = Color.Black,
                            errorBorderColor = Color.Red,
                            focusedContainerColor = Color(0xFFF3EDED),
                            unfocusedContainerColor = Color(0xFFF3EDED),
                            cursorColor = Color.Black
                        ),
                        textStyle = TextStyle(
                            fontFamily = montserratFontFamily,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                            .background(
                                color = Color(0xFFF3EDED),
                                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = Color.Black,
                                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                            )
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
                                            fontFamily = montserratFontFamily,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 16.sp,
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
