package com.frontend.buhoeats.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun FormField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    isMultiline: Boolean = false,
    placeholderText: String = ""
) {
    Column {
        Text(label, fontWeight = FontWeight.SemiBold, color = Color.Black , fontSize = 16.sp, modifier = Modifier.padding(bottom = 4.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            maxLines = if (isMultiline) 4 else 1,
            shape = RoundedCornerShape(12.dp),
            placeholder = {
                if (placeholderText.isNotBlank()) {
                    Text(text = placeholderText, color = Color.Gray)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                focusedContainerColor = Color.White.copy(alpha = 0.95f)
            )
        )
    }
}
