package com.frontend.buhoeats.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.frontend.buhoeats.R


@Composable
fun StaticProfileImage(userImageUrl: String) {
    if (userImageUrl.isNotBlank()) {
        AsyncImage(
            model = userImageUrl,
            contentDescription = "Imagen de perfil",
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
                .border(1.dp, Color.White, CircleShape),
            contentScale = ContentScale.Crop
        )
    } else {
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
}
