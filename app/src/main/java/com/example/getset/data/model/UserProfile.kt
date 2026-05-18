package com.example.getset.data.model

data class UserProfile(
    val gender: String = "",
    val height: String = "",
    val myWeight: String = "",
    val wantWeight: String = "",
    val purposes: List<String> = emptyList(),
    val attentionAreas: List<String> = emptyList(),
    val workouts: List<Workout> = emptyList(),
    val exerciseProgress: Map<String, List<ExerciseProgress>> = emptyMap()
)

data class Workout(
    val id: String = "",
    val title: String = "",
    val date: String = "",
    val exercises: List<String> = emptyList(),
    val notes: String = ""
)

data class ExerciseProgress(
    val date: String = "",
    val weight: Float = 0f,
    val reps: Int = 0,
    val sets: Int = 0
)