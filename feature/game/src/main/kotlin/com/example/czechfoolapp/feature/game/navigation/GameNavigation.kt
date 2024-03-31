package com.example.czechfoolapp.feature.game.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.czechfoolapp.feature.game.GameRoute

const val GAME_ROUTE = "game"

const val GAME_ID_ARG = "gameId"
fun NavController.navigateToGame(gameId: Int) =
    navigate("$GAME_ROUTE/$gameId")

fun NavGraphBuilder.gameRoute(
    onNavigateCancel: () -> Unit,
    windowWidth: WindowWidthSizeClass
) {
    composable(
        route = "$GAME_ROUTE/{$GAME_ID_ARG}",
        arguments = listOf(
            navArgument(GAME_ID_ARG) { type = NavType.IntType }
        )
    ) {
        GameRoute(
            windowWidth = windowWidth,
            onCancel = onNavigateCancel
        )
    }
}