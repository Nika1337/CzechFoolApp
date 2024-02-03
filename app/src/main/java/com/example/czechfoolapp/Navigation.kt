package com.example.czechfoolapp

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.czechfoolapp.ui.routes.gameoptionsroute.navigation.gameOptionsRoute
import com.example.czechfoolapp.ui.routes.gameoptionsroute.navigation.navigateToGameOptions
import com.example.czechfoolapp.ui.routes.gameroute.navigation.gameRoute
import com.example.czechfoolapp.ui.routes.gameroute.navigation.navigateToGame
import com.example.czechfoolapp.ui.routes.gameshistoryroute.navigation.GAMES_HISTORY_ROUTE
import com.example.czechfoolapp.ui.routes.gameshistoryroute.navigation.gamesHistoryRoute
import com.example.czechfoolapp.ui.routes.gameshistoryroute.navigation.popBackStackUpToGamesHistory
import com.example.czechfoolapp.ui.routes.nameinputroute.navigation.nameInputRoute
import com.example.czechfoolapp.ui.routes.nameinputroute.navigation.navigateToNameInputRoute


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