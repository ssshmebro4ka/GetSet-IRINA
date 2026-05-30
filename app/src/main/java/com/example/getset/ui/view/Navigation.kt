package com.example.getset.ui.view

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.getset.model.Screen

import com.example.getset.ui.theme.MyPorposeTrain
import com.example.getset.viewmodel.MainViewModel

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
            MyPorposeTrain(navController = navController,
                viewModel = viewModel)
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