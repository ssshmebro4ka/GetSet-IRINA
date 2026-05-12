package com.example.getset.ui.theme

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DataBCh(onBackClick: () -> Unit = {}, navController: NavHostController){
    var gender by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var myweight by remember { mutableStateOf("") }
    var wantweight by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val genderOptions= listOf("Жеснский","Мужской")
    val isFormValid by remember {
        derivedStateOf {
            gender.isNotBlank() && height.isNotBlank() && myweight.isNotBlank() && wantweight.isNotBlank()
        }
    }
    Box(modifier = Modifier.fillMaxWidth()){
        Column (
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(onClick = {navController.popBackStack() },
                    modifier = Modifier.size(48.dp)) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = " ",
                        tint = Color(0xFFF117C00),
                        modifier = Modifier.size(45.dp)
                    )
                }

            }
            Text(
                text = "Изменить мои",
                fontSize = 50.sp,
                fontWeight= FontWeight.Bold,
                color= Color(0xFFF117C00),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp),
            )

            Text(
                text = "данные",
                fontSize = 50.sp,
                fontWeight= FontWeight.Bold,
                color= Color(0xFFF117C00),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
            )
            Text(
                text = "Пол",
                fontSize = 45.sp,
                fontWeight= FontWeight.Bold,
                color= Color(0xFFF117C00),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
            )
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {expanded=it}) {
                OutlinedTextField(
                    value = gender,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)},
                    label={ Text("Введите пол", fontSize = 20.sp)},
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFFE7F4D2),
                        focusedContainerColor  = Color(0xFFFA1D05A),
                        focusedLabelColor = Color(0xFFF117C00),
                        unfocusedLabelColor = Color(0xFFF117C00),
                        focusedBorderColor = Color(0xFFF117C00),
                        unfocusedBorderColor = Color(0xFFF117C00)
                    ),

                    shape= RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {expanded=false},
                    modifier = Modifier.background(Color(0xFFFE7F4D2))
                ) {
                    genderOptions.forEach { option->
                        DropdownMenuItem(
                            text={Text(option, fontSize = 18.sp)},
                            onClick = {
                                gender=option
                                expanded=false
                            }
                        )
                    }
                }
            }

            Text(
                text = "Рост",
                fontSize = 45.sp,
                fontWeight= FontWeight.Bold,
                color= Color(0xFFF117C00),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
            )
            OutlinedTextField(
                value = height,
                onValueChange = {height=it},
                label={ Text("Введите рост", fontSize = 20.sp)},
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFFE7F4D2),
                    focusedContainerColor  = Color(0xFFFA1D05A),
                    focusedLabelColor = Color(0xFFF117C00),
                    unfocusedLabelColor = Color(0xFFF117C00),
                    focusedBorderColor = Color(0xFFF117C00),
                    unfocusedBorderColor = Color(0xFFF117C00)
                ),
                shape= RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Текущий вес",
                fontSize = 45.sp,
                fontWeight= FontWeight.Bold,
                color= Color(0xFFF117C00),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
            )
            OutlinedTextField(
                value = myweight,
                onValueChange = {myweight=it},
                label={ Text("Введите текущий вес", fontSize = 20.sp)},
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFFE7F4D2),
                    focusedContainerColor  = Color(0xFFFA1D05A),
                    focusedLabelColor = Color(0xFFF117C00),
                    unfocusedLabelColor = Color(0xFFF117C00),
                    focusedBorderColor = Color(0xFFF117C00),
                    unfocusedBorderColor = Color(0xFFF117C00)
                ),
                shape= RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Желаемый вес",
                fontSize = 45.sp,
                fontWeight= FontWeight.Bold,
                color= Color(0xFFF117C00),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
            )
            OutlinedTextField(
                value = wantweight,
                onValueChange = {wantweight=it},
                label={ Text("Введите желаемый вес", fontSize = 20.sp)},
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFFE7F4D2),
                    focusedContainerColor  = Color(0xFFFA1D05A),
                    focusedLabelColor = Color(0xFFF117C00),
                    unfocusedLabelColor = Color(0xFFF117C00),
                    focusedBorderColor = Color(0xFFF117C00),
                    unfocusedBorderColor = Color(0xFFF117C00)
                ),
                shape= RoundedCornerShape(15.dp),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = {
                if(isFormValid){
                    println("Регистрация:$gender/$height/$myweight/$wantweight")
                }
            },
                enabled = isFormValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors= ButtonDefaults.buttonColors(
                    containerColor = if (isFormValid) Color(0xFFF117C00) else Color (0xFFFB7D092),
                    contentColor = if(isFormValid) Color(0xFFFFFFEFE) else Color(color = 0xFFF117C00) ,
                    disabledContainerColor = Color(0xFFFB7D092),
                    disabledContentColor = Color(0xFFF117C00)
                ),

                )
            {
                Text(text="Сохранить",
                    fontSize =20.sp)
            }
        }
    }
}