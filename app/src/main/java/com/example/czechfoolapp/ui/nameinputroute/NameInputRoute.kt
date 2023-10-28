package com.example.czechfoolapp.ui.nameinputroute

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun NameInputRoute(
    onNavigateUp: () -> Unit,
    onNavigateToNext: () -> Unit
) {
    val viewModel: NameInputViewModel = viewModel()
    NameInputScreen(
        onNavigateToNext = onNavigateToNext,
        onNavigateUp = onNavigateUp,
        onEvent = viewModel::onEvent,
        nameInputState = viewModel.playerNameState.value,
    )
}

