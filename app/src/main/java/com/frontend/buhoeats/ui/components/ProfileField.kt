package com.frontend.buhoeats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frontend.buhoeats.ui.screens.montserratFontFamily

@Composable
fun ProfileField(
    label: String,
    value: String,
    isEditing: Boolean,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
    ) {
        Text(
            text = label,
            modifier = Modifier.align(Alignment.Start),
            style = TextStyle(
                fontFamily = montserratFontFamily,
                color = Color.Black,
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        if (isEditing) {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                textStyle = TextStyle(
                    fontFamily = montserratFontFamily,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFF3EDED))
                    .border(1.dp, Color.Black, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = value,
                    modifier = Modifier.padding(start = 16.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = montserratFontFamily
                    )
                )
            }
        }
    }
}
