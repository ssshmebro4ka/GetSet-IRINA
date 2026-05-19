package com.example.getset.theme

import com.example.getset.R
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.getset.model.UserProfile
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

@SuppressLint("InvalidColorHexValue")
@Composable
fun MyChangePurpose(onBackClick: () -> Unit = {}, navController: NavHostController){
    var selectedPurposes by remember { mutableStateOf<Set<String>>(emptySet()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var successMessage by remember { mutableStateOf("") }
    var isDataLoaded by remember { mutableStateOf(false) }

    val repository = remember { UserProfileRepository() }

    val allPurposes = listOf(
        "Стать сильнее",
        "Улучшить здоровье",
        "Сбросить вес",
        "Стать стройным и рельефным",
        "Набрать мышечную массу"
    )
    LaunchedEffect(Unit) {
        println("MyChangePurpose: Загрузка целей...")
        isDataLoaded = false
        repository.loadProfile { profile, error ->
            if (profile != null && profile.purposes.isNotEmpty()) {
                println("MyChangePurpose: Загружены цели: ${profile.purposes}")
                selectedPurposes = profile.purposes.toSet()
            } else {
                println("MyChangePurpose: Нет сохраненных целей")
                selectedPurposes = emptySet()
            }
            isDataLoaded = true
        }
    }

    fun togglePurpose(purpose: String) {
        selectedPurposes = if (selectedPurposes.contains(purpose)) {
            selectedPurposes - purpose
        } else {
            selectedPurposes + purpose
        }
    }

    val isOneChoose = selectedPurposes.isNotEmpty()

    Column (
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = " ",
                    tint = Color(0xFFF117C00),
                    modifier = Modifier.size(45.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Выберите цели",
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFF117C00),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        if (!isDataLoaded) {
            Text(
                text = "Загрузка данных...",
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(20.dp)
            )
        }

        allPurposes.forEach { purpose ->
            SelectableButtonChangePurpose(
                text = purpose,
                isSelected = selectedPurposes.contains(purpose),
                onClick = { togglePurpose(purpose) }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        Spacer(modifier = Modifier.height(48.dp))

        if (selectedPurposes.isNotEmpty()) {
            Text(
                text = "Выбрано: ${selectedPurposes.size} целей",
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

        if (successMessage.isNotBlank()) {
            Text(
                text = successMessage,
                color = Color(0xFFF117C00),
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = {
                if (isOneChoose && !isLoading) {
                    isLoading = true
                    errorMessage = ""
                    successMessage = ""

                    println("MyChangePurpose: СОХРАНЕНИЕ ЦЕЛЕЙ: $selectedPurposes")

                    val currentUser = Firebase.auth.currentUser
                    if (currentUser == null) {
                        errorMessage = "Пользователь не залогинен"
                        isLoading = false
                        return@Button
                    }

                    repository.loadProfile { profile, loadError ->
                        val existingProfile = profile ?: UserProfile()
                        val updatedProfile = existingProfile.copy(purposes = selectedPurposes.toList())

                        repository.saveProfile(updatedProfile) { success, saveError ->
                            isLoading = false

                            if (success) {
                                println("MyChangePurpose: Цели обновлены!")
                                successMessage = "Цели сохранены!"
                                navController.popBackStack()
                            } else {
                                println("MyChangePurpose: Ошибка: $saveError")
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
            Text(text = if (isLoading) "Сохранение..." else "Сохранить", fontSize = 20.sp)
        }
    }
}

@Composable
fun SelectableButtonChangePurpose(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
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
            ){
                if (isSelected){
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