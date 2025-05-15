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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.frontend.buhoeats.R

@Composable
fun ProfileImage(
    userImageUrl: String,
    onImageSelected: (Uri?) -> Unit
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        onImageSelected(uri)
    }

    Box(
        modifier = Modifier.size(180.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        when {
            selectedImageUri != null -> {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            userImageUrl.isNotBlank() -> {
                AsyncImage(
                    model = userImageUrl,
                    contentDescription = "Imagen de perfil del usuario",
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.White, CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            else -> {
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

        IconButton(
            onClick = { imagePickerLauncher.launch("image/*") },
            modifier = Modifier
                .offset(x = (-8).dp, y = (-8).dp)
                .size(44.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.camera),
                contentDescription = "Cambiar imagen"
            )
        }
    }
}
