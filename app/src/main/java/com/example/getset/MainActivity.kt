package com.example.getset

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.getset.model.Database_Helper
import com.example.getset.ui.theme.GetSetTheme
import com.example.getset.ui.view.AppNavigation
import com.example.getset.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var dbHelper: Database_Helper
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = Database_Helper(this)
        viewModel = MainViewModel(dbHelper)
        viewModel.loadData()
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
                val navController = rememberNavController()
                //ExercisesScreen(navController = navController,
                //               viewModel = viewModel
                //)
                //GetSetScreen(navController = navController)
                //com.example.getset.view.SignInScreen(navController = navController)
                //MyPurpose(navController = navController)
                //Warning(navController = navController)
                //DataB(navController = navController)
                //HomeScreen(navController = navController)
                //IScreen(navController = navController)
                //MyChangePurpose(navController = navController)
                //ChangeWarning(navController = navController)
                //DataBCh (navController = navController)
                //Secundomer(navController = navController)
            }
        }
    }
}
