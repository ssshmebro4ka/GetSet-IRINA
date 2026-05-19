package com.example.getset.ui.theme

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.getset.model.ExerciseProgress
import com.example.getset.model.UserProfile
import com.example.getset.theme.UserProfileRepository
import com.example.getset.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPorposeTrain(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    val exercises = viewModel.dataList.collectAsState().value
    var exerciseWeights by remember { mutableStateOf<Map<String, String>>(emptyMap()) }
    var expandedExercise by remember { mutableStateOf<String?>(null) }
    var tempWeight by remember { mutableStateOf("") }
    var tempReps by remember { mutableStateOf("") }
    var tempSets by remember { mutableStateOf("") }

    val repository = remember { UserProfileRepository() }

    fun loadProgress() {
        repository.loadProfile { profile, _ ->
            val newMap = mutableMapOf<String, String>()
            exercises.forEach { exercise ->
                val name = exercise["name"] as? String ?: ""
                val progress = profile?.exerciseProgress?.get(name)?.lastOrNull()
                if (progress != null) {
                    newMap[name] = "${progress.weight.toInt()} кг × ${progress.sets} × ${progress.reps}"
                }
            }
            exerciseWeights = newMap
        }
    }

    fun saveProgress(exerciseName: String, weight: Float, reps: Int, sets: Int) {
        val today = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date())
        val newProgress = ExerciseProgress(today, weight, reps, sets)

        repository.loadProfile { profile, _ ->
            val existingProfile = profile ?: UserProfile()
            val currentProgress = existingProfile.exerciseProgress.toMutableMap()
            val existingList = currentProgress[exerciseName]?.toMutableList() ?: mutableListOf()
            existingList.add(newProgress)
            currentProgress[exerciseName] = existingList

            val updatedProfile = existingProfile.copy(exerciseProgress = currentProgress)
            repository.saveProfile(updatedProfile) { success, _ ->
                if (success) {
                    loadProgress()
                    exerciseWeights = exerciseWeights + (exerciseName to "${weight.toInt()} кг × ${sets} × ${reps}")
                    println("Сохранено в Firebase: $exerciseName - ${weight}кг x $sets x $reps")
                } else {
                    println("Ошибка сохранения в Firebase")
                }
            }
        }
    }

    LaunchedEffect(exercises) {
        loadProgress()
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Мои упражнения",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFF117C00),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Назад",
                            tint = Color(0xFFF117C00)
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
            if (exercises.isEmpty()) {
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
                    items(exercises) { exercise ->
                        val name = exercise["name"] as? String ?: "Без названия"
                        val equipment = exercise["equipment"] as? String ?: ""
                        val isExpanded = expandedExercise == name

                        ExerciseCard(
                            name = name,
                            equipment = equipment,
                            lastWeight = exerciseWeights[name],
                            isExpanded = isExpanded,
                            onExpand = {
                                expandedExercise = if (isExpanded) null else name
                                tempWeight = ""
                                tempReps = ""
                                tempSets = ""
                            },
                            weightValue = tempWeight,
                            onWeightChange = { tempWeight = it },
                            repsValue = tempReps,
                            onRepsChange = { tempReps = it },
                            setsValue = tempSets,
                            onSetsChange = { tempSets = it },
                            onSave = {
                                val w = tempWeight.toFloatOrNull()
                                val r = tempReps.toIntOrNull()
                                val s = tempSets.toIntOrNull()
                                if (w != null && r != null && s != null && w > 0 && r > 0 && s > 0) {
                                    saveProgress(name, w, r, s)
                                    expandedExercise = null
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseCard(
    name: String,
    equipment: String,
    lastWeight: String?,
    isExpanded: Boolean,
    onExpand: () -> Unit,
    weightValue: String,
    onWeightChange: (String) -> Unit,
    repsValue: String,
    onRepsChange: (String) -> Unit,
    setsValue: String,
    onSetsChange: (String) -> Unit,
    onSave: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onExpand() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF117C00)  // Зеленый фон карточки
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Верхняя часть всегда видна
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White  // Белый текст
                    )
                    if (equipment.isNotBlank()) {
                        Text(
                            text = equipment,
                            fontSize = 12.sp,
                            color = Color.White.copy(alpha = 0.7f)  // Полупрозрачный белый
                        )
                    }
                }

                if (lastWeight != null) {
                    Text(
                        text = lastWeight,
                        fontSize = 14.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Развернутая панель
            if (isExpanded) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Добавить результат",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = weightValue,
                        onValueChange = onWeightChange,
                        placeholder = { Text("кг", fontSize = 12.sp, color = Color.White.copy(alpha = 0.5f)) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White.copy(alpha = 0.2f),
                            unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.White.copy(alpha = 0.3f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
                            unfocusedPlaceholderColor = Color.White.copy(alpha = 0.5f)
                        ),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = repsValue,
                        onValueChange = onRepsChange,
                        placeholder = { Text("раз", fontSize = 12.sp, color = Color.White.copy(alpha = 0.5f)) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White.copy(alpha = 0.2f),
                            unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.White.copy(alpha = 0.3f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
                            unfocusedPlaceholderColor = Color.White.copy(alpha = 0.5f)
                        ),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = setsValue,
                        onValueChange = onSetsChange,
                        placeholder = { Text("подх", fontSize = 12.sp, color = Color.White.copy(alpha = 0.5f)) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White.copy(alpha = 0.2f),
                            unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                            focusedIndicatorColor = Color.White,
                            unfocusedIndicatorColor = Color.White.copy(alpha = 0.3f),
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
                            unfocusedPlaceholderColor = Color.White.copy(alpha = 0.5f)
                        ),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onSave,
                    enabled = weightValue.isNotBlank() && repsValue.isNotBlank() && setsValue.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        disabledContainerColor = Color.White.copy(alpha = 0.5f)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Сохранить",
                        modifier = Modifier.size(18.dp),
                        tint = Color(0xFFF117C00)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Сохранить",
                        color = Color(0xFFF117C00),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}