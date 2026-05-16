package com.example.getset.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.getset.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

@SuppressLint("InvalidColorHexValue")
@Composable
fun Warning(navController: NavHostController) {
    var selectedAreas by remember { mutableStateOf<Set<String>>(emptySet()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val repository = remember { UserProfileRepository() }

    val allAreas = listOf(
        "Спина",
        "Руки",
        "Грудь",
        "Ноги",
        "Ягодицы",
        "Пресс"
    )

    fun toggleArea(area: String) {
        selectedAreas = if (selectedAreas.contains(area)) {
            selectedAreas - area
        } else {
            selectedAreas + area
        }
    }

    val isOneChoose = selectedAreas.isNotEmpty()
    LaunchedEffect(Unit) {
        println("Warning: Загрузка областей внимания...")
        repository.loadProfile { profile, error ->
            if (profile != null && profile.attentionAreas.isNotEmpty()) {
                println("Warning: Загружены области: ${profile.attentionAreas}")
                selectedAreas = profile.attentionAreas.toSet()
            }
        }
    }

    Column (
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Text(
            text = "GetSet",
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF117C00),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Выберите области внимания",
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFF117C00),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))
        allAreas.forEach { area ->
            SelectableButtonWarning(
                text = area,
                isSelected = selectedAreas.contains(area),
                onClick = { toggleArea(area) }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (selectedAreas.isNotEmpty()) {
            Text(
                text = "Выбрано: ${selectedAreas.size} областей",
                fontSize = 16.sp,
                color = Color(0xFFF117C00),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        if (errorMessage.isNotBlank()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Button(
            onClick = {
                if (isOneChoose && !isLoading) {
                    isLoading = true
                    errorMessage = ""

                    println("Warning: СОХРАНЕНИЕ ОБЛАСТЕЙ: $selectedAreas")

                    val currentUser = Firebase.auth.currentUser
                    if (currentUser == null) {
                        errorMessage = "Пользователь не залогинен"
                        isLoading = false
                        return@Button
                    }
                    repository.loadProfile { profile, loadError ->
                        val existingProfile = profile ?: UserProfile()
                        val updatedProfile = existingProfile.copy(attentionAreas = selectedAreas.toList())

                        repository.saveProfile(updatedProfile) { success, saveError ->
                            isLoading = false

                            if (success) {
                                println("Warning: Области внимания сохранены!")
                                navController.navigate(Screen.DataB.route) {
                                    popUpTo(0) { inclusive = true }
                                }
                            } else {
                                println("Warning: Ошибка: $saveError")
                                errorMessage = saveError ?: "Ошибка сохранения"
                            }
                        }
                    }
                }
            },
            enabled = isOneChoose && !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF117C00),
                disabledContainerColor = Color(0xFFFB7D092),
                contentColor = Color.White,
                disabledContentColor = Color(0xFFF117C00)
            )
        ) {
            Text(text = if (isLoading) "Сохранение..." else "Далее", fontSize = 20.sp)
        }
    }
}

@Composable
fun SelectableButtonWarning(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFFF117C00) else Color(0xFFFB7D092),
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 4.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isSelected) {
                    Icon(
                        painter = painterResource(id = R.drawable.group),
                        contentDescription = " ",
                        modifier = Modifier.size(20.dp),
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text,
                    fontSize = 20.sp,
                    color = if (isSelected) Color.White else Color(0xFFF117C00)
                )
            }
        }
    }
}