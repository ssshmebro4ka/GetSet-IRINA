package com.example.getset.ui.theme

import SignInScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.getset.GetSetScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.getset.MyPurpose

sealed class Screen(val route: String) {
    object Registration : Screen("registration")
    object SignIn : Screen("signin")
    object MyPurpose : Screen("my_purpose")
    object Warning: Screen("warning")
    object DataB: Screen("data")
    object Home: Screen("home")
    object Profile: Screen("profile")
    object MyReadyTrain: Screen("readytrain")
    object MyTrain: Screen("mytrain")
    object Exersize: Screen("exersize")
    object MyPorpouseTrain: Screen("myporposetrain")
    object Secundomer: Screen("secundomer")

}
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Registration.route
    ) {
        composable(Screen.Registration.route) {
            GetSetScreen(navController = navController)
        }
        composable(Screen.SignIn.route) {
            SignInScreen(navController=navController)
        }
        composable(Screen.MyPurpose.route){
            MyPurpose(navController=navController)
        }
        composable(Screen.Warning.route){
            Warning(navController=navController)
        }
        composable(Screen.DataB.route){
            DataB(navController=navController)
        }
        composable(Screen.Home.route){
            HomeScreen(navController=navController)
        }
        composable(Screen.Profile.route){
            IScreen(navController=navController)
        }
        composable(Screen.MyReadyTrain.route){
            ReadyTrain(navController)
        }
        composable(Screen.MyTrain.route){
            MyTrain(navController=navController)
        }
        composable(Screen.Exersize.route){
            Exersize(navController=navController)
        }
        composable(Screen.MyPorpouseTrain.route){
            MyPorposeTrain(navController=navController)
        }
        composable(Screen.Secundomer.route){
            Secundomer(navController=navController)
        }
    }
}


