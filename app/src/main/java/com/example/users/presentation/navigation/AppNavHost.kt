package com.example.users.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.users.presentation.screens.DisplayScreen
import com.example.users.presentation.screens.InputScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.INPUT) {
        composable(Routes.INPUT) {
            InputScreen(
                onUserAdded = {
                    navController.navigate(Routes.DISPLAY)
                }
            )
        }

        composable(Routes.DISPLAY) {
            DisplayScreen()
        }
    }
}
