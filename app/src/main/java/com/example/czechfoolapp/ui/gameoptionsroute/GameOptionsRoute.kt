package com.example.czechfoolapp.ui.gameoptionsroute

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameOptionsRoute(
    onNavigateUp: () -> Unit,
    onNavigateToNext: () -> Unit
) {
    val viewModel: GameOptionsViewModel = viewModel(factory = GameOptionsViewModel.factory)
    val gameOptionState = viewModel.gameOptionsState
    GameOptionsScreen(
        onNavigateUp = onNavigateUp,
        onNavigateToNext = onNavigateToNext,
        gameOptionsState = gameOptionState,
        onEvent = viewModel::onEvent
    )
}