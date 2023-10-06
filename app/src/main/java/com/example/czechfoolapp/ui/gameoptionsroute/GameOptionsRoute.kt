package com.example.czechfoolapp.ui.gameoptionsroute

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameOptionsRoute(
    onNavigateUp: () -> Unit,
    onNavigateToNext: () -> Unit
) {
    val viewModel: GameOptionsViewModel = viewModel() // TODO factory and DI
    GameOptionsScreen(
        onNavigateUp = onNavigateUp,
        onNavigateToNext = onNavigateToNext,
        gameOptionState = viewModel.gameOptionsState,
        onEvent = viewModel::onEvent
    )
}