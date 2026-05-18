package com.example.getset.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.getset.navigation.Screen
import com.example.getset.presentation.components.ProfileTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignInScreen( // ЭТО ВХОД
    navController: NavHostController,
    viewModel: AuthViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is AuthEffect.NavigateToHome -> {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }

                is AuthEffect.NavigateToPurpose -> {
                    navController.navigate(Screen.MyPurpose.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }

                is AuthEffect.ShowError -> { /* Handle if needed */
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
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

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Вход",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF117C00),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            ProfileTextField(
                value = state.login,
                onValueChange = { viewModel.handleIntent(AuthIntent.UpdateLogin(it)) },
                label = "Почта"
            )

            Spacer(modifier = Modifier.height(40.dp))

            ProfileTextField(
                value = state.password,
                onValueChange = { viewModel.handleIntent(AuthIntent.UpdatePassword(it)) },
                label = "Пароль"
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { viewModel.handleIntent(AuthIntent.SignIn) },
                enabled = state.isFormValid && !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.isFormValid) Color(0xFF117C00) else Color(0xFFB7D092),
                    contentColor = if (state.isFormValid) Color(0xFFFFFEFE) else Color(0xFF117C00),
                    disabledContainerColor = Color(0xFFB7D092),
                    disabledContentColor = Color(0xFF117C00)
                ),
            ) {
                Text(text = if (state.isLoading) "Вход..." else "Войти", fontSize = 20.sp)
            }

            state.errorMessage?.let { error ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = error,
                    color = Color.Red,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}