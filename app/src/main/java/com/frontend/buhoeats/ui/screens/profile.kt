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
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar

import com.frontend.buhoeats.models.User
import com.frontend.buhoeats.ui.components.ProfileImage


val montserratFontFamily = FontFamily(
    Font(R.font.montserrat_bold)
)
@Composable
fun Profile(navController: NavController, user: User) {
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
                    .padding(32.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 6.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
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

                Column(   modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Nombre:",
                        modifier = Modifier.align(Alignment.Start),
                        style = TextStyle(
                            fontFamily = montserratFontFamily,
                            color = Color.Black,
                            fontSize = 16.sp,

                            )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp))
                            .background(Color(0xFFF3EDED))
                            .border(
                                1.dp,
                                Color.Black,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = user.name,
                            modifier = Modifier.padding(start = 16.dp),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = montserratFontFamily
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Apellido:",
                        modifier = Modifier.align(Alignment.Start),
                        style = TextStyle(
                            fontFamily = montserratFontFamily,
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp))
                            .background(Color(0xFFF3EDED))
                            .border(
                                1.dp,
                                Color.Black,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = user.lastName,
                            modifier = Modifier.padding(start = 16.dp),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = montserratFontFamily
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))


                    Text(
                        text = "Correo:",
                        modifier = Modifier.align(Alignment.Start),
                        style = TextStyle(
                            fontFamily = montserratFontFamily,
                            color = Color.Black,
                            fontSize = 16.sp
                        )
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp))
                            .background(Color(0xFFF3EDED))
                            .border(
                                1.dp,
                                Color.Black,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = user.email,
                            modifier = Modifier.padding(start = 16.dp),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = montserratFontFamily
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { /* editar perfil*/ },
                        modifier = Modifier
                            .width(200.dp)
                            .height(56.dp)
                            .shadow(elevation = 8.dp, shape = RoundedCornerShape(8.dp)),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC11D0C))
                    ) {
                        Text(
                            text = "Editar Perfil",
                            style = TextStyle(
                                fontFamily = montserratFontFamily,
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        )
                    } }
            }
        }
    }
}
