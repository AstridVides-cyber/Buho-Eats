package com.frontend.buhoeats.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frontend.buhoeats.R

val montserratFontFamily = FontFamily(
    Font(R.font.montserrat_bold)
)
@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    textColor: Color = Color.Black,
    containerColor: Color = Color(0xFFE0E0E0),
    isPassword: Boolean = false,
    modifier: Modifier = Modifier,
    validateDigits: Boolean = false,
    contextMessage: String = ""
) {
    val context = LocalContext.current

    Column(modifier = modifier) {
        Text(
            text = "$label:",
            style = TextStyle(
                fontFamily = montserratFontFamily,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )

        TextField(
            value = value,
            onValueChange = {
                var newValue = it
                if (validateDigits && it.any { c -> c.isDigit() }) {
                    Toast.makeText(context, contextMessage, Toast.LENGTH_SHORT).show()
                    newValue = it.filter { c -> !c.isDigit() }
                }
                onValueChange(newValue)
            },
            placeholder = {
                Text(
                    placeholder,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    style = TextStyle(fontFamily = montserratFontFamily)
                )
            },
            textStyle = TextStyle(
                color = textColor,
                fontSize = 16.sp,
                fontFamily = montserratFontFamily
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp), // altura igual a la del disabled
            shape = RoundedCornerShape(12.dp), // igual que el disabled
            colors = TextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                errorContainerColor = containerColor,
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                disabledIndicatorColor = Color.Black,
                errorIndicatorColor = Color.Black,
                cursorColor = textColor
            )
        )
    }
}
