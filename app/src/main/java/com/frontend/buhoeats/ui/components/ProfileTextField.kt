package com.frontend.buhoeats.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun ProfileTextField(
    label: String,
    value: TextFieldValue,
    onChange: (TextFieldValue) -> Unit,
    isEmail: Boolean = false
) {
    val shape = RoundedCornerShape(12.dp)
    val backgroundColor = Color(0xFFE0E0E0)

    Text(
        text = label,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp
    )

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        placeholder = { Text(label.removeSuffix(":")) },
        singleLine = true,
        shape = shape,
        keyboardOptions = if (isEmail) KeyboardOptions(keyboardType = KeyboardType.Email) else KeyboardOptions.Default,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            cursorColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedPlaceholderColor = Color.DarkGray,
            unfocusedPlaceholderColor = Color.DarkGray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(bottom = 5.dp)
    )
}
