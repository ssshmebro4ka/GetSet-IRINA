package com.example.getset.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.getset.presentation.workout.ExercisesScreen
import com.example.getset.GetSetScreen

import com.example.getset.MainViewModel
import com.example.getset.presentation.auth.SignInScreen
import com.example.getset.presentation.home.HomeScreen
import com.example.getset.presentation.profile.DataBCh
import com.example.getset.presentation.profile.DataEntryScreen
import com.example.getset.presentation.profile.IScreen
import com.example.getset.presentation.purpose.ChangePurposeScreen
import com.example.getset.presentation.purpose.PurposeScreen
import com.example.getset.presentation.timer.Secundino
import com.example.getset.presentation.warning.ChangeWarning
import com.example.getset.presentation.warning.WarningScreen
import com.example.getset.presentation.workout.MyPorposeTrain
import com.example.getset.presentation.workout.MyTrain
import com.example.getset.presentation.workout.WorkoutDetailScreen


sealed class Screen(val route: String) {
    object WorkoutDetail : Screen("workout_detail/{workoutId}") {
        fun passWorkoutId(workoutId: String) = "workout_detail/$workoutId"
    }

    object Registration : Screen("registration")
    object SignIn : Screen("signin")
    object MyPurpose : Screen("my_purpose")
    object Warning : Screen("warning")
    object DataEntry : Screen("data_entry") // Исправили название маршрута
    object Home : Screen("home")
    object Profile : Screen("profile")
    object MyTrain : Screen("my_train")
    object Exercise : Screen("exercise") // Исправили орфографию
    object MyPurposeTrain : Screen("my_purpose_train") // Исправили орфографию
    object Stopwatch : Screen("stopwatch") // Переименовали секундомер
    object ChangePurpose : Screen("change_purpose") // Исправили орфографию
    object ChangeData : Screen("change_data")
    object ChangeWarning : Screen("change_warning")
}

@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Registration.route,
    ) {
        composable(Screen.Registration.route) {
            GetSetScreen(navController = navController)
        }

        composable(Screen.SignIn.route) {
            SignInScreen(navController = navController)
        }

        composable(Screen.MyPurpose.route) {
            PurposeScreen(navController = navController)
        }

        composable(Screen.Warning.route) {
            WarningScreen(navController = navController)
        }

        composable(Screen.DataEntry.route) {
            DataEntryScreen(navController = navController) // Было DataScreen/DataB
        }

        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.Profile.route) {
            IScreen(navController = navController) // Было IScreen
        }

        composable(Screen.MyTrain.route) {
            MyTrain(navController = navController) // Было MyTrain
        }

        composable(Screen.Exercise.route) {
            ExercisesScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Screen.MyPurposeTrain.route) {
            MyPorposeTrain( // Было MyPorposeTrain
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Screen.Stopwatch.route) {
            Secundino(navController = navController) // Было Secundino
        }

        composable(Screen.ChangePurpose.route) {
            ChangePurposeScreen(navController = navController)
        }

        composable(Screen.ChangeData.route) {
            DataBCh(navController = navController) // Убедись, что функция называется так (было DataBCh)
        }

        composable(Screen.ChangeWarning.route) {
            ChangeWarning(navController = navController) // Было ChangeWarning
        }

        composable(
            route = Screen.WorkoutDetail.route,
            arguments = listOf(navArgument("workoutId") { type = NavType.StringType })
        ) { backStackEntry ->
            val workoutId = backStackEntry.arguments?.getString("workoutId") ?: ""
            WorkoutDetailScreen(
                navController = navController,
                workoutId = workoutId
            )
        }
    }
}