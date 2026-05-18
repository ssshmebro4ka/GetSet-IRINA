package com.example.getset.presentation.purpose

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.getset.R
import com.example.getset.navigation.Screen
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("InvalidColorHexValue")
@Composable
fun PurposeScreen(
    navController: NavHostController,
    viewModel: PurposeViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is PurposeEffect.NavigateToWarning -> {
                    navController.navigate(Screen.Warning.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }

                is PurposeEffect.NavigateBack -> {
                    navController.popBackStack()
                }

                is PurposeEffect.ShowError -> {}
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
        Text(
            text = "GetSet",
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF117C00),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Выберите цели",
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF117C00),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))
        viewModel.allPurposes.forEach { purpose ->
            SelectableButton(
                text = purpose,
                isSelected = state.selectedPurposes.contains(purpose),
                onClick = { viewModel.handleIntent(PurposeIntent.TogglePurpose(purpose)) }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (state.selectedPurposes.isNotEmpty()) {
            Text(
                text = "Выбрано: ${state.selectedPurposes.size} целей",
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

        Button(
            onClick = { viewModel.handleIntent(PurposeIntent.SavePurposes) },
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
            Text(text = if (state.isLoading) "Сохранение..." else "Далее", fontSize = 20.sp)
        }
    }
}

@Composable
fun SelectableButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
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