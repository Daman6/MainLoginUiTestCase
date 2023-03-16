package com.example.mainloginuitestcase

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetUpNavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController,
            startDestination = Screen.Home.route
    ){
        composable(
            route = Screen.Home.route
        ){
            HomeScreen(navController = navHostController)
        }
        composable(
            route = Screen.Main.route
        ){
            MainScreen(navController = navHostController)
        }
    }
    
}