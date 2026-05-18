package com.example.getset.presentation.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import com.example.getset.presentation.main.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExercisesScreen(
    navController: NavHostController,
    viewModel: MainViewModel? = null
) {
    val actualViewModel = viewModel ?: viewModel()
    val dataList by actualViewModel.dataList.collectAsState()

    var selectedExerciseMap by remember { mutableStateOf<Map<String, Any>?>(null) }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (selectedExerciseMap == null) "Упражнения" else (selectedExerciseMap?.get(
                            "name"
                        ) as? String ?: "Детали"),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF117C00),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (selectedExerciseMap != null) {
                            selectedExerciseMap = null
                        } else {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_left),
                            contentDescription = "Назад",
                            tint = Color(0xFF117C00),
                            modifier = Modifier.size(45.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            if (selectedExerciseMap == null) {
                if (dataList.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Нет упражнений",
                            fontSize = 18.sp,
                            color = Color.Gray
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(dataList) { exerciseMap ->
                            val name = exerciseMap["name"] as? String ?: "Без названия"

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedExerciseMap = exerciseMap },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFF117C00)
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = name,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.White
                                    )
                                    Text(
                                        text = "›",
                                        fontSize = 28.sp,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                ExerciseDetailCard(exerciseMap = selectedExerciseMap!!)
            }
        }
    }
}

@Composable
fun ExerciseDetailCard(exerciseMap: Map<String, Any>) {
    val name = exerciseMap["name"] as? String ?: "Название не указано"
    val equipment = exerciseMap["equipment"] as? String ?: "Не указано"
    val difficulty = exerciseMap["difficulty"] as? String ?: "Не указана"
    val technique = exerciseMap["technique"] as? String ?: "Техника не указана"
    val tips = exerciseMap["tips"] as? String ?: "Советов нет"
    val precautions = exerciseMap["precautions"] as? String ?: "Меры предосторожности не указаны"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF117C00),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F5F5)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Инвентарь",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = equipment,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF117C00)
                        )
                    }
                }

                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F5F5)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Сложность",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = difficulty,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF117C00)
                        )
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Техника выполнения",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF117C00)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            ) {
                Text(
                    text = technique,
                    fontSize = 16.sp,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Советы",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF117C00)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            ) {
                Text(
                    text = tips,
                    fontSize = 16.sp,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Меры предосторожности",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF117C00)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            ) {
                Text(
                    text = precautions,
                    fontSize = 16.sp,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}