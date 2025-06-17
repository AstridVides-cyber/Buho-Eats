package com.frontend.buhoeats.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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
    var showMenu by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf<RoleOption?>(null) }
    var showValidationErrors by remember { mutableStateOf(false) }

    val roleOptions = listOf(
        RoleOption("Super Administrador", R.drawable.super_admin),
        RoleOption("Administrador de Local", R.drawable.admi_local),
        RoleOption("Usuario", R.drawable.ususario)
    )

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
                    modifier = Modifier.padding(top = 50.dp, bottom = 20.dp)
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
                        errorBorderColor = Color.Black,
                        focusedContainerColor = Color(0xFFF3EDED),
                        unfocusedContainerColor = Color(0xFFF3EDED),
                        disabledContainerColor = Color(0xFFF3EDED),
                        errorContainerColor = Color(0xFFF3EDED),
                        cursorColor = Color.Black
                    )
                )

                if (emailError.isNotEmpty()) {
                    Text(
                        text = emailError,
                        color = Color.Red,
                        fontSize = 14.sp,
                        fontFamily = montserratFontFamily,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 25.dp, top = 4.dp)
                    )
                }

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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .border(1.dp, Color.Black, RoundedCornerShape(16.dp))
                        .background(Color(0xFFF3EDED), RoundedCornerShape(16.dp))
                        .clickable { showMenu = !showMenu }
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = selectedRole?.label ?: "Seleccione un rol",
                            fontFamily = montserratFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (selectedRole == null) Color.Gray else Color.Black
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }

                    if (showMenu) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )

                        roleOptions.forEach { role ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedRole = role
                                        showMenu = false
                                    }
                                    .padding(vertical = 10.dp)
                            ) {
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
                        }
                    }
                }

                if (showValidationErrors && selectedRole == null) {
                    Text(
                        text = "Seleccione un rol",
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 25.dp, top = 4.dp),
                        fontFamily = montserratFontFamily
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                val isEmailValid = email.contains("@") && email.contains(".")
                val isFormValid = isEmailValid && selectedRole != null

                Button(
                    onClick = {
                        showValidationErrors = true
                        if (!isEmailValid) {
                            emailError = "Ingrese un correo válido"
                        } else {
                            emailError = ""
                        }

                        if (isFormValid) {
                            println("Correo: $email")
                            println("Rol: ${selectedRole?.label}")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF06BB0C),
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.White
                    ),
                    modifier = Modifier
                        .width(200.dp)
                        .height(55.dp)
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Confirmar", fontFamily = montserratFontFamily, fontSize = 18.sp)
                }
            }
        }
    }
}
