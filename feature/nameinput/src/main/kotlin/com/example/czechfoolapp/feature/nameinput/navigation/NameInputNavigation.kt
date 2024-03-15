package com.example.czechfoolapp.feature.nameinput.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.czechfoolapp.ui.routes.nameinputroute.NameInputRoute

const val NAME_INPUT_ROUTE= "name_input"

const val LOSING_SCORE_ARG = "losingScore"
const val NUMBER_OF_PLAYERS_ARG = "numberOfPlayers"

fun NavController.navigateToNameInputRoute(
    losingScore: Int,
    numberOfPlayers: Int
) = navigate("$NAME_INPUT_ROUTE/$losingScore/$numberOfPlayers")

fun NavGraphBuilder.nameInputRoute(
    onNavigateUp: () -> Unit,
    onNavigateToNext: () -> Unit
) {
    composable(
        route = "$NAME_INPUT_ROUTE/{$LOSING_SCORE_ARG}/{$NUMBER_OF_PLAYERS_ARG}",
        arguments = listOf(
            navArgument(LOSING_SCORE_ARG) { type = NavType.IntType },
            navArgument(NUMBER_OF_PLAYERS_ARG) { type = NavType.IntType }
        )
    ) {
        NameInputRoute(
            onNavigateUp = onNavigateUp,
            onNavigateToNext = onNavigateToNext
        )
    }
}