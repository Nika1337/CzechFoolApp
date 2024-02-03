package com.example.czechfoolapp.ui.routes.gameoptionsroute

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameOptionsRoute(
    onNavigateUp: () -> Unit,
    onNavigateToNext: (Int, Int) -> Unit
) {
    val viewModel: GameOptionsViewModel = viewModel(factory = GameOptionsViewModel.factory)
    val gameOptionState by viewModel.gameOptionsState.collectAsStateWithLifecycle()
    GameOptionsScreen(
        onNavigateUp = onNavigateUp,
        onNavigateToNext = onNavigateToNext,
        gameOptionsState = gameOptionState,
        onEvent = viewModel::onEvent
    )
}