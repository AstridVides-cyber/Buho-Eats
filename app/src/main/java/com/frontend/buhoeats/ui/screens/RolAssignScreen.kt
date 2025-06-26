package com.frontend.buhoeats.ui.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.ui.components.ValidationMessage
import com.frontend.buhoeats.ui.theme.ThemeManager
import com.frontend.buhoeats.utils.Translations
import com.frontend.buhoeats.viewmodel.UserSessionViewModel

data class RoleOption(val label: String, @DrawableRes val imageRes: Int)

fun getRoleValue(label: String): String = when (label) {
    "Super Administrador" -> "superadmin"
    "Administrador de Local" -> "admin"
    "Usuario" -> "usuario"
    else -> "usuario"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RolAssign(
    navController: NavController,
    userSessionViewModel: UserSessionViewModel
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var showMenu by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf<RoleOption?>(null) }
    var showValidationErrors by remember { mutableStateOf(false) }


    val roleOptions = listOf(
        RoleOption(Translations.t("role_superadmin"), R.drawable.super_admin),
        RoleOption(Translations.t("role_admin"), R.drawable.admi_local),
        RoleOption(Translations.t("role_user"), R.drawable.ususario)
    )


    val backgroundImage = if (ThemeManager.isDarkTheme)
    painterResource(id = R.drawable.backgrounddark)
    else
    painterResource(id = R.drawable.backgroundlighttheme)

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
                painter = backgroundImage,
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
                    text = Translations.t("assign_roles_title"),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFontFamily,
                    modifier = Modifier.padding(top = 50.dp, bottom = 20.dp)
                )

                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    Translations.t("email_label"),
                    color = MaterialTheme.colorScheme.onBackground,
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
                            Translations.t("email_placeholder_admin"),
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
                    ValidationMessage(
                        message = emailError,
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }


                Spacer(modifier = Modifier.height(25.dp))

                Text(
                    Translations.t("role_type_label"),
                    color = MaterialTheme.colorScheme.onBackground,
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
                        .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(16.dp))
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
                            text = selectedRole?.label ?: Translations.t("select_role"),
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
                    ValidationMessage(
                        message = Translations.t("select_role_error"),
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )
                }


                Spacer(modifier = Modifier.height(30.dp))

                val isEmailValid = email.contains("@") && email.contains(".")
                val isFormValid = isEmailValid && selectedRole != null

                Button(
                    onClick = {
                        val roleValue = getRoleValue(selectedRole?.label ?: "")

                        showValidationErrors = true

                        emailError = if (!isEmailValid) {
                            Translations.t("invalid_email")
                        } else {
                            ""
                        }
                        if (isFormValid) {
                            val currentUser by userSessionViewModel.currentUser

                            if (currentUser?.rol != "superadmin") {
                                emailError = Translations.t("only_superadmins_can_assign")
                                return@Button
                            }

                            val success = userSessionViewModel.assignRoleToUser(email.toString(), roleValue.toString())

                            if (success) {
                                Toast.makeText(context, Translations.t("role_assigned_success"), Toast.LENGTH_SHORT).show()
                                email = ""
                                selectedRole = null
                                showValidationErrors = false
                                userSessionViewModel.loadUsers()
                                navController.popBackStack()

                            } else {
                                emailError = Translations.t("user_not_found")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF06BB0C),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.size(width = 220.dp, height = 50.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(Translations.t("confirm"), fontFamily = montserratFontFamily, fontSize = 18.sp)
                }
            }
        }
    }
}
