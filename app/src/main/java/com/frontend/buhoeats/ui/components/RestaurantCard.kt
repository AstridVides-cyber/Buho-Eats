package com.frontend.buhoeats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.frontend.buhoeats.models.ContactInfo
import com.frontend.buhoeats.models.Dish
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import com.frontend.buhoeats.models.Comment
import com.frontend.buhoeats.models.Rating
import com.frontend.buhoeats.models.User
import com.frontend.buhoeats.ui.theme.AppColors
import com.frontend.buhoeats.utils.Translations

@Composable
fun ContactCard(contactInfo: ContactInfo) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = AppColors.secondary,
            contentColor = Color.White
        ),
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Email, contentDescription = "Correo", tint = MaterialTheme.colorScheme.surface)
                Spacer(modifier = Modifier.width(8.dp))
                Text("${Translations.t("contact_email")}: ${contactInfo.email}", color = MaterialTheme.colorScheme.surface)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Phone, contentDescription = "Teléfono", tint = MaterialTheme.colorScheme.surface)
                Spacer(modifier = Modifier.width(8.dp))
                Text("${Translations.t("contact_phone")}: ${contactInfo.phone}", color = MaterialTheme.colorScheme.surface)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AccessTime, contentDescription = "Horario", tint = MaterialTheme.colorScheme.surface)
                Spacer(modifier = Modifier.width(8.dp))
                Text("${Translations.t("contact_hours")} ${contactInfo.hours}", color = MaterialTheme.colorScheme.surface)
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.LocationOn, contentDescription = "Dirección", tint = MaterialTheme.colorScheme.surface)
                Spacer(modifier = Modifier.width(8.dp))
                Text("${Translations.t("contact_address")}: ${contactInfo.address}", color = MaterialTheme.colorScheme.surface)
            }
        }
    }
}

@Composable
fun DishCard(
    dish: Dish,
    showDelete: Boolean = false,
    onDelete: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            AsyncImage(
                model = dish.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )

            if (showDelete) {
                DeleteButton(
                    onClick = onDelete,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(AppColors.secondary.copy(alpha = 0.9f))
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        dish.name,
                        color = AppColors.text,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        "$${dish.price}",
                        color = AppColors.text,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Text(
                    dish.description,
                    color = AppColors.text,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun Opinion(user: User?, comment: Comment, rating: Rating?) {
    Row(modifier = Modifier.padding(vertical = 4.dp)) {
        Icon(Icons.Filled.Person, contentDescription = "Persona")
        Spacer(modifier = Modifier.width(10.dp))

        Column {
            Text(text = user?.let { "${it.name} ${it.lastName}" } ?: Translations.t("unknown_user"), fontWeight = FontWeight.Bold)
            Text(text = comment.comment)

            rating?.let {
                Row {
                    repeat(it.rating) {
                        Icon(Icons.Filled.Star, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(20.dp))
                    }
                    repeat(5 - it.rating) {
                        Icon(Icons.Outlined.Star, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    }
}



@Composable
fun RatingBar(rating: Int, onRatingChanged: (Int) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        for (i in 1..5) {
            IconButton(
                onClick = { onRatingChanged(i) },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "${Translations.t("star_description")} $i",
                    tint = if (i <= rating) Color(0xFFFFC107) else Color.Gray,
                    modifier = Modifier.size(30.dp)

                )
            }
        }
    }
}


