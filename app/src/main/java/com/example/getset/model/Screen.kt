package com.example.getset.model

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