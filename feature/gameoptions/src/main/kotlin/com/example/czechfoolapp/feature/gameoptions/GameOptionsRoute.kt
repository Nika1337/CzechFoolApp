package com.example.czechfoolapp.feature.gameoptions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun GameOptionsRoute(
    onNavigateUp: () -> Unit,
    onNavigateToNext: (Int, Int) -> Unit
) {
    val viewModel: GameOptionsViewModel = hiltViewModel()
    val gameOptionState by viewModel.gameOptionsState.collectAsStateWithLifecycle()
    GameOptionsScreen(
        onNavigateUp = onNavigateUp,
        onNavigateToNext = onNavigateToNext,
        gameOptionsState = gameOptionState,
        onEvent = viewModel::onEvent
    )
}