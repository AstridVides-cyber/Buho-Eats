package com.frontend.buhoeats.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Photo
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.frontend.buhoeats.R
import com.frontend.buhoeats.models.Restaurant
import com.frontend.buhoeats.ui.components.BottomNavigationBar
import com.frontend.buhoeats.ui.components.TopBar
import com.frontend.buhoeats.ui.theme.AppColors
import com.frontend.buhoeats.ui.theme.ThemeManager
import com.frontend.buhoeats.utils.Translations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRestaurantScreen(
    navController: NavHostController,
    onBack: () -> Unit = {},
    onEditImages: (String) -> Unit = {},
    onEditMenu: () -> Unit = {},
    onEditInfo: () -> Unit = {},
    restaurant: Restaurant
) {
    val backgroundImage = if (ThemeManager.isDarkTheme)
        painterResource(id = R.drawable.backgrounddark)
    else
        painterResource(id = R.drawable.backgroundlighttheme)

    Scaffold(
        topBar = {
            TopBar(
                showBackIcon = true,
                onNavClick = onBack
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
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

            Column(modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp)) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onEditImages(restaurant.id) }
                        .padding(vertical = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Photo,
                        contentDescription = Translations.t("edit_image"),
                        tint = AppColors.texto,
                        modifier = Modifier.size(45.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(Translations.t("edit_image"), fontSize = 22.sp, color = AppColors.texto)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = null,
                        modifier = Modifier
                            .graphicsLayer { rotationY = 180f }
                            .size(28.dp)
                    )
                }
                Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.3f))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onEditMenu() }
                        .padding(vertical = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.MenuBook,
                        contentDescription = Translations.t("edit_menu"),
                        modifier = Modifier.size(45.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(Translations.t("edit_menu"), fontSize = 22.sp, color = AppColors.texto)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = null,
                        modifier = Modifier
                            .graphicsLayer { rotationY = 180f }
                            .size(28.dp)
                    )
                }
                Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.3f))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onEditInfo() }
                        .padding(vertical = 10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.EditNote,
                        contentDescription = Translations.t("edit_info"),
                        tint = AppColors.texto,
                        modifier = Modifier.size(45.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(Translations.t("edit_info"), fontSize = 22.sp, color = AppColors.texto)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = null,
                        modifier = Modifier
                            .graphicsLayer { rotationY = 180f }
                            .size(28.dp)
                    )
                }

                Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.3f))
            }
        }
    }
}
