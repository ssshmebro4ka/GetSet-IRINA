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
fun SignUpScreen( // ЭТО РЕГИСТРАЦИЯ
    navController: NavHostController,
    viewModel: AuthViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is AuthEffect.NavigateToPurpose -> {
                    navController.navigate(Screen.MyPurpose.route) {
                        popUpTo(Screen.Registration.route) { inclusive = true }
                    }
                }

                is AuthEffect.NavigateToHome -> {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }

                is AuthEffect.ShowError -> { /* Handle error */
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

            Text(
                text = "Регистрация",
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
                onClick = { viewModel.handleIntent(AuthIntent.SignUp) },
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
                Text(
                    text = if (state.isLoading) "Регистрация..." else "Зарегистрироваться",
                    fontSize = 20.sp
                )
            }

            state.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(text = "Уже есть аккаунт ?", fontSize = 20.sp, color = Color(0xFF117C00))

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { navController.navigate(Screen.SignIn.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF117C00),
                    contentColor = Color(0xFFFFFEFE)
                )
            ) {
                Text("Войти", fontSize = 20.sp)
            }
        }
    }
}