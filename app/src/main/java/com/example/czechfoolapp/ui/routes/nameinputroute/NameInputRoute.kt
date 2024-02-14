package com.example.czechfoolapp.ui.routes.nameinputroute

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun NameInputRoute(
    onNavigateUp: () -> Unit,
    onNavigateToNext: () -> Unit
) {
    val viewModel: NameInputViewModel = viewModel(factory = NameInputViewModel.factory)
    val nameInputState by viewModel.playerNamesState.collectAsStateWithLifecycle()
    BackHandler {
        onNavigateUp()
    }
    NameInputScreen(
        onNavigateToNext = onNavigateToNext,
        onNavigateUp = onNavigateUp,
        onEvent = viewModel::onEvent,
        nameInputState = nameInputState
    )
}

