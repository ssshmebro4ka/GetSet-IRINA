package com.example.getset

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.getset.data.local.DatabaseHelper
import com.example.getset.navigation.AppNavigation
import com.example.getset.presentation.main.MainViewModel
import com.example.getset.ui.theme.GetSetTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dbHelper = DatabaseHelper(this)
        viewModel = MainViewModel(dbHelper)

        lifecycleScope.launch {
            try {
                viewModel.loadData()
            } catch (e: Exception) {
                println("Ошибка загрузки данных: ${e.message}")
            }
        }

        enableEdgeToEdge()

        setContent {
            GetSetTheme {
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}