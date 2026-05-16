package com.example.getset.ui.theme

import SignInScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.getset.GetSetScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.getset.ExercisesScreen
import com.example.getset.MainViewModel
import com.example.getset.MyPurpose

sealed class Screen(val route: String) {
    object Workouts : Screen("workouts")
    object WorkoutDetail : Screen("workout_detail/{workoutId}") {
        fun passWorkoutId(workoutId: String) = "workout_detail/$workoutId"
    }
    object Registration : Screen("registration")
    object SignIn : Screen("signin")
    object MyPurpose : Screen("my_purpose")
    object Warning: Screen("warning")
    object DataB: Screen("data")
    object Home: Screen("home")
    object Profile: Screen("profile")
    object MyTrain: Screen("mytrain")
    object Exersize: Screen("exersize")
    object MyPorpouseTrain: Screen("myporposetrain")
    object Secundomer: Screen("secundomer")
    object ChangePorpose: Screen("chageporpose")
    object ChangeData: Screen("changedata")
    object ChangeWarning: Screen("changewarning")
}

@Composable
fun AppNavigation(viewModel: MainViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Registration.route
    ) {
        composable(Screen.Registration.route) {
            GetSetScreen(navController = navController)
        }

        composable(Screen.SignIn.route) {
            SignInScreen(navController = navController)
        }

        composable(Screen.MyPurpose.route) {
            MyPurpose(navController = navController)
        }

        composable(Screen.Warning.route) {
            Warning(navController = navController)
        }

        composable(Screen.DataB.route) {
            DataB(navController = navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(Screen.Profile.route) {
            IScreen(navController = navController)
        }

        composable(Screen.MyTrain.route) {
            MyTrain(navController = navController)
        }

        composable(Screen.Exersize.route) {
            ExercisesScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Screen.MyPorpouseTrain.route) {
            MyPorposeTrain(navController = navController)
        }

        composable(Screen.Secundomer.route) {
            Secundomer(navController = navController)
        }

        composable(Screen.ChangePorpose.route) {
            MyChangePurpose(navController = navController)
        }

        composable(Screen.ChangeData.route) {
            DataBCh(navController = navController)
        }

        composable(Screen.ChangeWarning.route) {
            ChangeWarning(navController = navController)
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