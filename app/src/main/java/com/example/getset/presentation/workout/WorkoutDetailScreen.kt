package com.example.getset.presentation.workout

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
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
import com.example.getset.data.model.UserProfile
import com.example.getset.data.model.Workout
import com.example.getset.data.repository.UserProfileRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailScreen(
    navController: NavHostController,
    workoutId: String
) {
    var workout by remember { mutableStateOf<Workout?>(null) }
    var newExercise by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val repository = remember { UserProfileRepository() }

    fun loadWorkout() {
        repository.loadProfile { profile, _ ->
            val foundWorkout = profile?.workouts?.find { it.id == workoutId }
            if (foundWorkout != null) {
                workout = foundWorkout
                notes = foundWorkout.notes
            }
        }
    }

    fun saveWorkout(updatedWorkout: Workout) {
        repository.loadProfile { profile, _ ->
            val existingProfile = profile ?: UserProfile()
            val updatedWorkouts = existingProfile.workouts.map {
                if (it.id == workoutId) updatedWorkout else it
            }
            val updatedProfile = existingProfile.copy(workouts = updatedWorkouts)
            repository.saveProfile(updatedProfile) { success, _ ->
                if (success) {
                    workout = updatedWorkout
                    println("WorkoutDetail: Тренировка обновлена")
                }
            }
        }
    }

    fun addExercise() {
        if (newExercise.isNotBlank() && workout != null) {
            val updatedWorkout = workout!!.copy(
                exercises = workout!!.exercises + newExercise,
                notes = notes
            )
            saveWorkout(updatedWorkout)
            newExercise = ""
        }
    }

    fun deleteExercise(exercise: String) {
        if (workout != null) {
            val updatedWorkout = workout!!.copy(
                exercises = workout!!.exercises.filter { it != exercise },
                notes = notes
            )
            saveWorkout(updatedWorkout)
        }
    }

    fun saveNotes() {
        if (workout != null) {
            val updatedWorkout = workout!!.copy(notes = notes)
            saveWorkout(updatedWorkout)
        }
    }

    LaunchedEffect(workoutId) {
        loadWorkout()
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = workout?.title ?: "Загрузка...",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF117C00),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_left),
                            contentDescription = "Назад",
                            tint = Color(0xFF117C00),
                            modifier = Modifier.size(45.dp)
                        )
                    }
                },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            if (workout == null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Загрузка...",
                        fontSize = 18.sp,
                        color = Color(0xFF117C00)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text(
                            text = "Упражнения",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF117C00)
                        )
                    }

                    items(workout!!.exercises) { exercise ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF5F5F5)
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = exercise,
                                    fontSize = 16.sp,
                                    color = Color(0xFF333333),
                                    modifier = Modifier.weight(1f)
                                )
                                IconButton(
                                    onClick = { deleteExercise(exercise) },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_delete),
                                        contentDescription = "Удалить",
                                        tint = Color(0xFF117C00),
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                value = newExercise,
                                onValueChange = { newExercise = it },
                                placeholder = {
                                    Text(
                                        "Новое упражнение",
                                        color = Color.Gray
                                    )
                                },
                                modifier = Modifier.weight(1f),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color(0xFF117C00),
                                    unfocusedIndicatorColor = Color(0xFFCCCCCC),
                                    focusedTextColor = Color(0xFF333333),
                                    unfocusedTextColor = Color(0xFF333333)
                                )
                            )
                            Button(
                                onClick = { addExercise() },
                                enabled = newExercise.isNotBlank(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF117C00),
                                    disabledContainerColor = Color(0xFFCCCCCC)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "Добавить",
                                    color = Color.White,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Заметки",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF117C00)
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        TextField(
                            value = notes,
                            onValueChange = { notes = it },
                            placeholder = {
                                Text(
                                    "Ваши заметки о тренировке...",
                                    color = Color.Gray
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 4,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color(0xFFFAFAFA),
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                focusedIndicatorColor = Color(0xFF117C00),
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedTextColor = Color(0xFF333333),
                                unfocusedTextColor = Color(0xFF333333)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = { saveNotes() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF117C00)
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                        ) {
                            Text(
                                text = "Сохранить заметки",
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
        }
    }
}