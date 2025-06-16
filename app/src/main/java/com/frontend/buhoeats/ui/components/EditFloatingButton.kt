package com.frontend.buhoeats.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EditFloatingButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = Color(0xFF06BB0C),
        contentColor = Color.White,
        modifier = modifier
            .size(70.dp),
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Editar",
            modifier = Modifier.size(45.dp)
        )
    }
}
