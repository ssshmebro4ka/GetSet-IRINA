package com.example.getset.ui.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun FavoriteTrain(navController: NavHostController) {
    Text(
        text = "избранные тренировки",
    )
}