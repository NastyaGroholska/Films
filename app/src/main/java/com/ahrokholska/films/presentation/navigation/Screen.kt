package com.ahrokholska.films.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object HomeScreen : Screen()

    @Serializable
    data class DetailsScreen(val id: Int) : Screen()
}