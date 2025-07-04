package com.frontend.buhoeats.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.frontend.buhoeats.R
import com.frontend.buhoeats.utils.ImageConverter

@Composable
fun ProfileImage(
    userImageUrl: String,
    onImageSelected: (String) -> Unit // Ahora recibe String en lugar de Uri?
) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let {
            // CAMBIO MÍNIMO: Convertir URI a Base64 automáticamente
            val base64Image = ImageConverter.uriToBase64(context, it)
            if (base64Image != null) {
                onImageSelected(base64Image) // Envía Base64 en lugar de URI string
            }
        }
    }

    Box(
        modifier = Modifier.size(120.dp),
        contentAlignment = Alignment.Center
    ) {
        // CAMBIO MÍNIMO: Detectar tipo de imagen automáticamente
        when {
            ImageConverter.isBase64(userImageUrl) -> {
                // Es Base64 - convertir a Bitmap
                val bitmap = ImageConverter.base64ToBitmap(userImageUrl)
                if (bitmap != null) {
                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Fallback si falla la conversión
                    DefaultProfileImage()
                }
            }
            userImageUrl.isNotEmpty() -> {
                // Es URL - usar AsyncImage como antes
                AsyncImage(
                    model = userImageUrl,
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            else -> {
                // Sin imagen - mostrar default
                DefaultProfileImage()
            }
        }

        IconButton(
            onClick = { imagePickerLauncher.launch("image/*") },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset((-8).dp, (-8).dp)
                .size(36.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.camera),
                contentDescription = "Edit Profile",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun DefaultProfileImage() {
    Image(
        painter = painterResource(id = R.drawable.defaulticon),
        contentDescription = "Default Profile",
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .border(2.dp, Color.White, CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun StaticProfileImage(
    imageUrl: String,
    size: androidx.compose.ui.unit.Dp = 120.dp
) {
    // CAMBIO MÍNIMO: Detectar tipo de imagen automáticamente (solo lectura)
    when {
        ImageConverter.isBase64(imageUrl) -> {
            // Es Base64 - convertir a Bitmap
            val bitmap = ImageConverter.base64ToBitmap(imageUrl)
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(size)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Fallback
                DefaultStaticProfileImage(size)
            }
        }
        imageUrl.isNotEmpty() -> {
            // Es URL - usar AsyncImage como antes
            AsyncImage(
                model = imageUrl,
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        else -> {
            // Sin imagen - mostrar default
            DefaultStaticProfileImage(size)
        }
    }
}

@Composable
private fun DefaultStaticProfileImage(size: androidx.compose.ui.unit.Dp) {
    Image(
        painter = painterResource(id = R.drawable.defaulticon),
        contentDescription = "Default Profile",
        modifier = Modifier
            .size(size)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}
