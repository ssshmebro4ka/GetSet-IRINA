package com.example.getset.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 20.sp) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color(0xFFE7F4D2),
            focusedContainerColor = Color(0xFFA1D05A),
            focusedLabelColor = Color(0xFF117C00),
            unfocusedLabelColor = Color(0xFF117C00),
            focusedBorderColor = Color(0xFF117C00),
            unfocusedBorderColor = Color(0xFF117C00)
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = modifier.fillMaxWidth()
    )
}