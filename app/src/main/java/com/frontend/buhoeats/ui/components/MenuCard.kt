package com.frontend.buhoeats.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Person
import coil.compose.AsyncImage


@Composable
fun MenuCard(nombre: String, descripcion: String, precio: String, imagenUrl: String) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = imagenUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(nombre, fontWeight = FontWeight.Bold)
                Text(descripcion)
            }
            Text(precio, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun RatingBar(rating: Int) {
    Row {
        repeat(5) { index ->
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Estrella",
                tint = if (index < rating) Color(0xFFFFD700) else Color.LightGray
            )
        }
    }
}

@Composable
fun Opinion(nombre: String, comentario: String, estrellas: Int) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(Icons.Filled.Person, contentDescription = "Persona")
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(nombre, fontWeight = FontWeight.Bold)
            Text(comentario)
            RatingBar(rating = estrellas)
        }
    }
}
