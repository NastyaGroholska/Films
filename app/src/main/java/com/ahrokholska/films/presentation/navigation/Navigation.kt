package com.ahrokholska.films.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahrokholska.films.presentation.screens.details.DetailsScreen
import com.ahrokholska.films.presentation.screens.home.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.HomeScreen) {
        composable<Screen.HomeScreen> {
            HomeScreen(onItemClick = { navController.navigate(Screen.DetailsScreen(id = it)) })
        }

        composable<Screen.DetailsScreen> {
            DetailsScreen()
        }
    }
}