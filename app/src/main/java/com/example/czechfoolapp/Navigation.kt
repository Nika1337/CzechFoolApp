package com.example.czechfoolapp

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.czechfoolapp.feature.game.navigation.gameRoute
import com.example.czechfoolapp.feature.game.navigation.navigateToGame
import com.example.czechfoolapp.feature.gameoptions.navigation.gameOptionsRoute
import com.example.czechfoolapp.feature.gameoptions.navigation.navigateToGameOptions
import com.example.czechfoolapp.feature.gameshistory.navigation.GAMES_HISTORY_ROUTE
import com.example.czechfoolapp.feature.gameshistory.navigation.gamesHistoryRoute
import com.example.czechfoolapp.feature.gameshistory.navigation.popBackStackUpToGamesHistory
import com.example.czechfoolapp.feature.nameinput.navigation.nameInputRoute
import com.example.czechfoolapp.feature.nameinput.navigation.navigateToNameInputRoute


@Composable
fun CzechFoulNavHost(
    windowWidth: WindowWidthSizeClass,
    navController: NavHostController = rememberNavController(),
    startDestination: String = GAMES_HISTORY_ROUTE
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        gamesHistoryRoute(
            onNavigateStartNewGame = { navController.navigateToGameOptions() },
            onNavigateContinueNewGame = { navController.navigateToGame() },
            windowWidth = windowWidth
        )
        gameOptionsRoute(
            navigateUp = navController::navigateUp,
            onNavigateToNext = { losingScore: Int, numberOfPlayers: Int ->
                navController.navigateToNameInputRoute(
                    losingScore = losingScore,
                    numberOfPlayers = numberOfPlayers
                )
            }
        )
        nameInputRoute(
            onNavigateUp = navController::navigateUp,
            onNavigateToNext = { navController.navigateToGame() }
        )
        gameRoute(
            onNavigateCancel = { navController.popBackStackUpToGamesHistory(false) },
            windowWidth = windowWidth
        )
    }
}