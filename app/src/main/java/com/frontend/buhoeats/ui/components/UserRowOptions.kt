package com.frontend.buhoeats.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.frontend.buhoeats.R
import com.frontend.buhoeats.models.User
import com.frontend.buhoeats.ui.theme.AppColors
import com.frontend.buhoeats.utils.ImageConverter
import com.frontend.buhoeats.utils.Translations
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource

@Composable
fun UserRowOptions(
    user: User,
    onConfirmAction: () -> Unit,
    modifier: Modifier = Modifier,
    confirmationMessage: String = Translations.t("confirmationMessage"),
    iconTint: Color = Color(0xFF4CAF50)
) {
    var showDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.fondo)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // CAMBIO: Detectar tipo de imagen automáticamente en UserRowOptions
                when {
                    !user.imageProfile.isNullOrBlank() && ImageConverter.isBase64(user.imageProfile) -> {
                        // Es Base64 - convertir a Bitmap
                        val bitmap = ImageConverter.base64ToBitmap(user.imageProfile)
                        if (bitmap != null) {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "Foto de perfil",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                            )
                        } else {
                            // Fallback si falla la conversión
                            Image(
                                painter = androidx.compose.ui.res.painterResource(R.drawable.defaulticon),
                                contentDescription = "Foto de perfil",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                            )
                        }
                    }
                    !user.imageProfile.isNullOrBlank() -> {
                        // Es URL - usar AsyncImage como antes
                        AsyncImage(
                            model = user.imageProfile,
                            contentDescription = "Foto de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                    }
                    else -> {
                        // Sin imagen - usar default
                        Image(
                            painter = androidx.compose.ui.res.painterResource(R.drawable.defaulticon),
                            contentDescription = "Foto de perfil",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "${user.name} ${user.lastName}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(55.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = "Opciones del usuario",
                    tint = iconTint
                )
            }

            if (showDialog) {
                ConfirmationDialog(
                    message = confirmationMessage,
                    onConfirm = {
                        showDialog = false
                        onConfirmAction()
                    },
                    onDismiss = { showDialog = false }
                )
            }
        }
    }
}
