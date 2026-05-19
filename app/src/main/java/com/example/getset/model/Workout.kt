package com.example.getset.model

data class Workout(
    val id: String = "",
    val title: String = "",
    val date: String = "",
    val exercises: List<String> = emptyList(),
    val notes: String = ""
)