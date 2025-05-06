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
    backgroundColor: Color,
    shape: RoundedCornerShape,
    isEmail: Boolean = false
) {
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
        label = { Text(label.removeSuffix(":")) },
        singleLine = true,
        shape = shape,
        keyboardOptions = if (isEmail) KeyboardOptions(keyboardType = KeyboardType.Email) else KeyboardOptions.Default,
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(bottom = 5.dp)
    )
}
