package com.example.getset.ui.theme

data class UserProfile(
    val gender: String = "",
    val height: String = "",
    val myweight: String = "",
    val wantweight: String = "",
    val purposes: List<String> = emptyList(),
    val attentionAreas: List<String> = emptyList()
)