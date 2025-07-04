package com.frontend.buhoeats.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.frontend.buhoeats.R
import com.frontend.buhoeats.utils.ImageConverter

@Composable
fun StaticProfileImage(userImageUrl: String) {
    // CAMBIO MÍNIMO: Detectar tipo de imagen automáticamente (solo lectura)
    when {
        ImageConverter.isBase64(userImageUrl) -> {
            // Es Base64 - convertir a Bitmap
            val bitmap = ImageConverter.base64ToBitmap(userImageUrl)
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Imagen de perfil",
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White, CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Fallback si falla la conversión Base64
                DefaultProfileImage()
            }
        }
        userImageUrl.isNotBlank() -> {
            // Es URL - usar AsyncImage como antes
            AsyncImage(
                model = userImageUrl,
                contentDescription = "Imagen de perfil",
                modifier = Modifier
                    .size(180.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.White, CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        else -> {
            // Sin imagen - mostrar default
            DefaultProfileImage()
        }
    }
}

@Composable
private fun DefaultProfileImage() {
    Image(
        painter = painterResource(id = R.drawable.defaulticon),
        contentDescription = "Imagen por defecto",
        modifier = Modifier
            .size(180.dp)
            .clip(CircleShape)
            .border(1.dp, Color.White, CircleShape),
        contentScale = ContentScale.Fit
    )
}
