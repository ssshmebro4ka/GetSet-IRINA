package com.example.getset.presentation.profile

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataBCh(
    onBackClick: () -> Unit = {},
    navController: NavHostController,
    viewModel: ProfileViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    val genderOptions = listOf("Женский", "Мужской")

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ProfileEffect.NavigateBack -> navController.popBackStack()
                is ProfileEffect.ShowToast -> { /* Handle toast if needed */
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(20.dp),
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

            Text(
                text = "Изменить мои",
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF117C00),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
            )
            Text(
                text = "данные",
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF117C00),
                modifier = Modifier
                    .fillMaxWidth()

            )

            if (!state.isDataLoaded) {
                Text(
                    text = "Загрузка данных...",
                    fontSize = 18.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(20.dp)
                )
            }

            Text(
                text = "Пол",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF117C00),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }) {
                val fillMaxWidth = Modifier
                    .fillMaxWidth()
                OutlinedTextField(
                    value = state.gender,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    label = { Text("Введите пол", fontSize = 20.sp) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFE7F4D2),
                        focusedContainerColor = Color(0xFFA1D05A),
                        focusedLabelColor = Color(0xFF117C00),
                        unfocusedLabelColor = Color(0xFF117C00),
                        focusedBorderColor = Color(0xFF117C00),
                        unfocusedBorderColor = Color(0xFF117C00)
                    ),
                    shape = RoundedCornerShape(15.dp),
                    modifier = fillMaxWidth.menuAnchor(
                        ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                        true
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color(0xFFE7F4D2))
                ) {
                    genderOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option, fontSize = 18.sp) },
                            onClick = {
                                viewModel.handleIntent(ProfileIntent.UpdateGender(option))
                                expanded = false
                            }
                        )
                    }
                }
            }

            Text(
                text = "Рост",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF117C00),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
            )
            OutlinedTextField(
                value = state.height,
                onValueChange = { viewModel.handleIntent(ProfileIntent.UpdateHeight(it)) },
                label = { Text("Введите рост", fontSize = 20.sp) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFE7F4D2),
                    focusedContainerColor = Color(0xFFA1D05A),
                    focusedLabelColor = Color(0xFF117C00),
                    unfocusedLabelColor = Color(0xFF117C00),
                    focusedBorderColor = Color(0xFF117C00),
                    unfocusedBorderColor = Color(0xFF117C00)
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Текущий вес",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF117C00),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
            )
            OutlinedTextField(
                value = state.myWeight,
                onValueChange = { viewModel.handleIntent(ProfileIntent.UpdateMyWeight(it)) },
                label = { Text("Введите текущий вес", fontSize = 20.sp) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFE7F4D2),
                    focusedContainerColor = Color(0xFFA1D05A),
                    focusedLabelColor = Color(0xFF117C00),
                    unfocusedLabelColor = Color(0xFF117C00),
                    focusedBorderColor = Color(0xFF117C00),
                    unfocusedBorderColor = Color(0xFF117C00)
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Желаемый вес",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF117C00),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
            )
            OutlinedTextField(
                value = state.wantWeight,
                onValueChange = { viewModel.handleIntent(ProfileIntent.UpdateWantWeight(it)) },
                label = { Text("Введите желаемый вес", fontSize = 20.sp) },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFE7F4D2),
                    focusedContainerColor = Color(0xFFA1D05A),
                    focusedLabelColor = Color(0xFF117C00),
                    unfocusedLabelColor = Color(0xFF117C00),
                    focusedBorderColor = Color(0xFF117C00),
                    unfocusedBorderColor = Color(0xFF117C00)
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

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
                onClick = { viewModel.handleIntent(ProfileIntent.SaveProfile) },
                enabled = state.isFormValid && !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (state.isFormValid && !state.isLoading) Color(0xFF117C00) else Color(
                        0xFFB7D092
                    ),
                    contentColor = if (state.isFormValid && !state.isLoading) Color(0xFFFFFEFE) else Color(
                        0xFF117C00
                    ),
                    disabledContainerColor = Color(0xFFB7D092),
                    disabledContentColor = Color(0xFF117C00)
                )
            ) {
                Text(text = if (state.isLoading) "Сохранение..." else "Сохранить", fontSize = 20.sp)
            }
        }
    }
}