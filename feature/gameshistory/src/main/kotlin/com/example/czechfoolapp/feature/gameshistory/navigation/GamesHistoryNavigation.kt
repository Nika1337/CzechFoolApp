package com.example.czechfoolapp.feature.gameshistory.navigation

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.czechfoolapp.feature.gameshistory.GamesHistoryRoute

const val GAMES_HISTORY_ROUTE = "games_history_route"

fun NavController.popBackStackUpToGamesHistory(inclusive: Boolean) =
    this.popBackStack(GAMES_HISTORY_ROUTE, inclusive)

fun NavGraphBuilder.gamesHistoryRoute(
    onNavigateStartNewGame: () -> Unit,
    onNavigateContinueNewGame: (Int) -> Unit,
    windowWidth: WindowWidthSizeClass
) {
    composable(
        route = GAMES_HISTORY_ROUTE
    ) {
        GamesHistoryRoute(
            onStartNewGame = onNavigateStartNewGame,
            onContinueGame = onNavigateContinueNewGame,
            windowWidth = windowWidth
        )
    }
}
