package com.example.czechfoolapp

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.czechfoolapp.Destinations.GAME_OPTIONS_ROUTE
import com.example.czechfoolapp.Destinations.GAME_ROUTE
import com.example.czechfoolapp.Destinations.NAME_INPUT_ROUTE
import com.example.czechfoolapp.ui.GameRoute
import com.example.czechfoolapp.ui.gameoptionsroute.GameOptionsRoute
import com.example.czechfoolapp.ui.nameinputroute.NameInputRoute


object Destinations {
    const val GAME_OPTIONS_ROUTE = "options_input"
    const val NAME_INPUT_ROUTE= "name_input"
    const val GAME_ROUTE = "game"
}
@Composable
fun CzechFoulNavHost(
    windowWidth: WindowWidthSizeClass,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = GAME_OPTIONS_ROUTE
    ) {
        composable(GAME_OPTIONS_ROUTE) {
            GameOptionsRoute(
                onNavigateUp = navController::navigateUp,
                onNavigateToNext = { navController.navigate(NAME_INPUT_ROUTE) }
            )
        }
        composable(NAME_INPUT_ROUTE) {
            NameInputRoute(
                onNavigateUp = navController::navigateUp,
                onNavigateToNext = { navController.navigate(GAME_ROUTE) }

            )
        }
        composable(GAME_ROUTE) {
            GameRoute(
                windowWidth = windowWidth,
                onCancel = { navController.navigate(GAME_OPTIONS_ROUTE) }
            )
        }
    }
}