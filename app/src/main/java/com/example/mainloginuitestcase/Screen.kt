package com.example.mainloginuitestcase

sealed class Screen(val route :String) {
    object Home: Screen(route = "home_screen")
    object Main: Screen(route = "main_screen")
}