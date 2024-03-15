package com.example.czechfoolapp.feature.gameoptions.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.czechfoolapp.ui.routes.gameoptionsroute.GameOptionsRoute

const val GAME_OPTIONS_ROUTE = "options_input"


fun NavController.navigateToGameOptions() = navigate(GAME_OPTIONS_ROUTE)
fun NavGraphBuilder.gameOptionsRoute(
    navigateUp: () -> Unit,
    onNavigateToNext: (Int, Int) -> Unit
) {
    composable(
        route = GAME_OPTIONS_ROUTE
    ) {
        GameOptionsRoute(
            onNavigateUp = navigateUp,
            onNavigateToNext = onNavigateToNext
        )
    }
}