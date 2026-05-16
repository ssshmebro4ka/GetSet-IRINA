package com.example.getset.ui.theme

data class UserProfile(
    val gender: String = "",
    val height: String = "",
    val myweight: String = "",
    val wantweight: String = "",
    val purposes: List<String> = emptyList(),
    val attentionAreas: List<String> = emptyList(),
    val workouts: List<Workout> = emptyList()
)
data class Workout(
    val id: String = "",
    val title: String = "",
    val date: String = "",
    val exercises: List<String> = emptyList(),
    val notes: String = ""
)