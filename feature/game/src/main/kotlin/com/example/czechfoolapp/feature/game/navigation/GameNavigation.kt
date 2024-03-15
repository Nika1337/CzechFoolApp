package com.example.czechfoolapp.feature.game.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.czechfoolapp.feature.game.GameRoute

const val GAME_ROUTE = "game"

fun NavController.navigateToGame() = navigate(GAME_ROUTE)

fun NavGraphBuilder.gameRoute(
    onNavigateCancel: () -> Unit,
    windowWidth: WindowWidthSizeClass
) {
    composable(GAME_ROUTE) {
        GameRoute(
            windowWidth = windowWidth,
            onCancel = onNavigateCancel
        )
    }
}