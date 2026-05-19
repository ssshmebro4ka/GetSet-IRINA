package com.example.getset.model


data class UserProfile(
    val gender: String = "",
    val height: String = "",
    val myweight: String = "",
    val wantweight: String = "",
    val purposes: List<String> = emptyList(),
    val attentionAreas: List<String> = emptyList(),
    val workouts: List<Workout> = emptyList(),
    val exerciseProgress: Map<String, List<ExerciseProgress>> = emptyMap()
)