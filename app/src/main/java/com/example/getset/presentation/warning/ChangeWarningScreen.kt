package com.example.getset.presentation.warning

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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.getset.R
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("InvalidColorHexValue")
@Composable
fun ChangeWarning(
    onBackClick: () -> Unit = {},
    navController: NavHostController,
    viewModel: WarningViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is WarningEffect.NavigateBack -> navController.popBackStack()
                is WarningEffect.NavigateToData -> { /* Not used here */ }
                is WarningEffect.ShowError -> { /* Handle error */ }
            }
        }
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = " ",
                    tint = Color(0xFF117C00),
                    modifier = Modifier.size(45.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Выберите области внимания (можно несколько)",
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF117C00),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        if (!state.isDataLoaded) {
            Text(
                text = "Загрузка данных...",
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(20.dp)
            )
        }

        viewModel.allAreas.forEach { area ->
            SelectableButtonChangeWarning(
                text = area,
                isSelected = state.selectedAreas.contains(area),
                onClick = { viewModel.handleIntent(WarningIntent.ToggleArea(area)) }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        Spacer(modifier = Modifier.height(48.dp))
        if (state.selectedAreas.isNotEmpty()) {
            Text(
                text = "Выбрано: ${state.selectedAreas.size} областей",
                fontSize = 16.sp,
                color = Color(0xFF117C00),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        state.errorMessage?.let { message ->
            Text(
                text = message,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        state.successMessage?.let { message ->
            Text(
                text = message,
                color = Color(0xFF117C00),
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Button(
            onClick = { viewModel.handleIntent(WarningIntent.ChangeAreas) },
            enabled = state.isReadyToNext && !state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF117C00),
                disabledContainerColor = Color(0xFFB7D092),
                contentColor = Color.White,
                disabledContentColor = Color(0xFF117C00)
            )
        ) {
            Text(text = if (state.isLoading) "Сохранение..." else "Сохранить", fontSize = 20.sp)
        }
    }
}

@Composable
fun SelectableButtonChangeWarning(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF117C00) else Color(0xFFB7D092),
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
                    color = if (isSelected) Color.White else Color(0xFF117C00)
                )
            }
        }
    }
}