package com.example.czechfoolapp.feature.nameinput

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun NameInputRoute(
    onNavigateUp: () -> Unit,
    onNavigateToNext: (Int) -> Unit
) {
    val viewModel: NameInputViewModel = hiltViewModel()
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

