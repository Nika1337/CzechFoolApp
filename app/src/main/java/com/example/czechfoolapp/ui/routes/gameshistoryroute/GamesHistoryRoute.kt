package com.example.czechfoolapp.ui.routes.gameshistoryroute

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.czechfoolapp.ui.routes.gameshistoryroute.util.GamesHistoryContentType
import com.example.czechfoolapp.ui.routes.gameshistoryroute.util.GamesHistoryCurrentScreen

@Composable
fun GamesHistoryRoute(
    onStartNewGame: () -> Unit,
    onContinueGame: () -> Unit,
    windowWidth: WindowWidthSizeClass
) {
    val gamesHistoryContentType = when(windowWidth) {
        WindowWidthSizeClass.Compact -> {
            GamesHistoryContentType.GAME_LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            GamesHistoryContentType.GAME_LIST_AND_DETAIL
        }
        WindowWidthSizeClass.Expanded -> {
            GamesHistoryContentType.GAME_LIST_AND_DETAIL
        }
        else -> {
            GamesHistoryContentType.GAME_LIST_ONLY
        }
    }

    val viewModel: GamesHistoryViewModel = hiltViewModel()

    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val gamesHistoryUiState by viewModel.gamesHistoryUiState.collectAsStateWithLifecycle()
    val currentGame by viewModel.currentChosenGame.collectAsStateWithLifecycle()

    if (gamesHistoryContentType == GamesHistoryContentType.GAME_LIST_ONLY) {
        if (currentScreen == GamesHistoryCurrentScreen.LIST){
            GamesHistoryScreen(
                gamesHistoryUiState = gamesHistoryUiState,
                onEvent = viewModel::onEvent,
                onNavigateStartNewGame = onStartNewGame,
                modifier = Modifier
                    .fillMaxSize()
            )
        } else {
            GameDetailScreen(
                game = currentGame,
                onEvent = viewModel::onEvent,
                onNavigateUp = {
                    viewModel.onEvent(GamesHistoryEvent.DetailScreenNavigateUp)
                },
                onNavigateContinueGame = onContinueGame,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    } else {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            GamesHistoryScreen(
                gamesHistoryUiState = gamesHistoryUiState,
                onEvent = viewModel::onEvent,
                onNavigateStartNewGame = onStartNewGame,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
            GameDetailScreen(
                game = currentGame,
                onEvent = viewModel::onEvent,
                onNavigateContinueGame = onContinueGame,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
        }
    }
}