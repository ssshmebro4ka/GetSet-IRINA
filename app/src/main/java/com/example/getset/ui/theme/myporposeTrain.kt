package com.example.getset


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPorposeTrain(
    navController: NavHostController,
    viewModel: MainViewModel? = null
) {
    Text("my porpose")
}