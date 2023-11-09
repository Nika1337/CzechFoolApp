package com.example.czechfoolapp.ui.nameinputroute

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun NameInputRoute(
    onNavigateUp: () -> Unit,
    onNavigateToNext: () -> Unit
) {
    val viewModel: NameInputViewModel = viewModel(factory = NameInputViewModel.factory)
    BackHandler {
        onNavigateUp()
    }
    NameInputScreen(
        onNavigateToNext = onNavigateToNext,
        onNavigateUp = {
            viewModel.onEvent(NameInputEvent.Back(onNavigateUp))
        },
        onEvent = viewModel::onEvent,
        nameInputState = viewModel.playerNameState.value,
    )
}

