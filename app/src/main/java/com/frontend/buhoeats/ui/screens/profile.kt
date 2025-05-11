package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.data.DummyData
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar

import com.frontend.buhoeats.models.User
import com.frontend.buhoeats.ui.components.ProfileField
import com.frontend.buhoeats.ui.components.ProfileImage


val montserratFontFamily = FontFamily(
    Font(R.font.montserrat_bold)
)
@Composable
fun Profile(navController: NavController, user: User) {
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(user.name) }
    var lastName by remember { mutableStateOf(user.lastName) }
    var email by remember { mutableStateOf(user.email) }

    Scaffold(
        topBar = { TopBar(showBackIcon = true) },
        bottomBar = { BottomNavigationBar() }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                    .padding(12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Mi cuenta",
                        style = TextStyle(
                            fontFamily = montserratFontFamily,
                            color = Color.Black,
                            fontSize = 34.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                ProfileImage(
                    userImageUrl = user.imageProfile,
                    onImageSelected = { newUri -> }
                )

                Spacer(modifier = Modifier.height(12.dp))

                ProfileField("Nombre:", name, isEditing) { name = it }
                Spacer(modifier = Modifier.height(12.dp))
                ProfileField("Apellido:", lastName, isEditing) { lastName = it }
                Spacer(modifier = Modifier.height(12.dp))
                ProfileField("Correo:", email, isEditing) { email = it }
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (isEditing) {
                            DummyData.updateUser(User(1, name, lastName, "", email))
                        }
                        isEditing = !isEditing
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .height(56.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp)),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC11D0C))
                ) {
                    Text(
                        text = if (isEditing) "Guardar Cambios" else "Editar Perfil",
                        style = TextStyle(
                            fontFamily = montserratFontFamily,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}
