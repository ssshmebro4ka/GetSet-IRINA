package com.example.getset.ui.theme

import android.R.attr.fontWeight
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.getset.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTrain(navController: NavHostController) {
    var workouts by remember { mutableStateOf<List<Workout>>(emptyList()) }
    var showAddDialog by remember { mutableStateOf(false) }

    val repository = remember { UserProfileRepository() }

    fun loadWorkouts() {
        println("MyTrain: Загрузка тренировок...")
        repository.loadProfile { profile, error ->
            if (profile != null) {
                workouts = profile.workouts
                println("MyTrain: Загружено ${workouts.size} тренировок")
            }
        }
    }

    fun saveWorkouts(newWorkouts: List<Workout>) {
        repository.loadProfile { profile, error ->
            val existingProfile = profile ?: UserProfile()
            val updatedProfile = existingProfile.copy(workouts = newWorkouts)
            repository.saveProfile(updatedProfile) { success, _ ->
                if (success) {
                    workouts = newWorkouts
                    println("MyTrain: Тренировки сохранены")
                }
            }
        }
    }

    fun addWorkout(title: String) {
        val newWorkout = Workout(
            id = UUID.randomUUID().toString(),
            title = title,
            date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date()),
            exercises = emptyList(),
            notes = ""
        )
        saveWorkouts(workouts + newWorkout)
    }
    fun deleteWorkout(workout: Workout) {
        saveWorkouts(workouts.filter { it.id != workout.id })
    }

    LaunchedEffect(Unit) {
        loadWorkouts()
    }
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Мои тренировки",
                        fontSize = 40.sp,
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
                            tint = Color(0xFFF117C00),
                            modifier = Modifier.size(45.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = Color(0xFFF117C00),
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Добавить")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            if (workouts.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painterResource(id = R.drawable.facesad),
                        contentDescription = " ",
                        tint = Color(0xFFF117C00),
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "У вас пока нет тренировок",
                        fontSize = 20.sp,
                        color = Color(0xFFF117C00),
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Нажмите на кнопку +, чтобы добавить",
                        fontSize = 16.sp,
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
                    items(workouts) { workout ->
                        WorkoutCard(
                            workout = workout,
                            onDelete = { deleteWorkout(workout) },
                            onClick = {
                                navController.navigate("workout_detail/${workout.id}")
                            }
                        )
                    }
                }
            }
        }
    }
    if (showAddDialog) {
        AddWorkoutDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { title ->
                if (title.isNotBlank()) {
                    addWorkout(title)
                    showAddDialog = false
                }
            }
        )
    }
}

@Composable
fun WorkoutCard(
    workout: Workout,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = workout.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF117C00)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = workout.date,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                if (workout.exercises.isNotEmpty()) {
                    Text(
                        text = "${workout.exercises.size} упражнений",
                        fontSize = 12.sp,
                        color = Color(0xFFF117C00)
                    )
                }
            }
            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Удалить",
                    tint = Color(0xFFF117C00)
                )
            }
        }
    }
}

@Composable
fun AddWorkoutDialog(
    onDismiss: () -> Unit,
    onAdd: (String) -> Unit
) {
    var title by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Новая тренировка",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFF117C00)
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Введите название тренировки",
                    color = Color(0xFF333333),
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = {
                        Text(
                            "Например: Тренировка спины",
                            color = Color.Gray
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color(0xFFF117C00),
                        unfocusedIndicatorColor = Color.Gray,
                        focusedTextColor = Color(0xFF333333),
                        unfocusedTextColor = Color(0xFF333333)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onAdd(title) },
                enabled = title.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF117C00),
                    disabledContainerColor = Color(0xFFCCCCCC)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Добавить", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF999999)
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Отмена", color = Color.White)
            }
        }
    )
}