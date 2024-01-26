package com.example.czechfoolapp.ui.gameroute

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.czechfoolapp.ui.gameroute.cardchoiceroute.CardChoiceEvent
import com.example.czechfoolapp.ui.gameroute.cardchoiceroute.CardChoiceScreen
import com.example.czechfoolapp.ui.gameroute.gameprogressroute.GameProgressScreen
import com.example.czechfoolapp.ui.gameroute.util.GameContentType
import com.example.czechfoolapp.ui.gameroute.util.GameCurrentScreen

@Composable
fun GameRoute(
    windowWidth: WindowWidthSizeClass,
    onCancel: () -> Unit
) {
    BackHandler{
        // Shouldn't do anything
    }

    val gameContentType = when(windowWidth) {
        WindowWidthSizeClass.Compact -> {
            GameContentType.PLAYER_LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            GameContentType.PLAYER_AND_CARD_LISTS
        }
        WindowWidthSizeClass.Expanded -> {
            GameContentType.PLAYER_AND_CARD_LISTS
        }
        else -> {
            GameContentType.PLAYER_LIST_ONLY
        }
    }

    val viewModel: GameViewModel = viewModel(factory = GameViewModel.factory)
    val currentScreen = viewModel.currentScreen
    val gameProgressState by viewModel.gameProgressState.collectAsStateWithLifecycle()
    val cardChoiceState by viewModel.cardChoiceState


    if (gameContentType == GameContentType.PLAYER_LIST_ONLY) {
        if (currentScreen == GameCurrentScreen.PLAYER_LIST) {
            GameProgressScreen(
                gameProgressState = gameProgressState,
                onNavigateCancel = onCancel,
                onEvent = viewModel::onGameProgressEvent,
                modifier = Modifier
                    .fillMaxSize()
            )
        } else if (currentScreen == GameCurrentScreen.CARD_LIST) {
            CardChoiceScreen(
                state = cardChoiceState,
                isWinnerScreen = viewModel.isWinnerState(),
                onEvent = viewModel::onCardChoiceEvent,
                onNavigateUp = { viewModel.onCardChoiceEvent(CardChoiceEvent.NavigateUp) },
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    } else if (gameContentType == GameContentType.PLAYER_AND_CARD_LISTS) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            GameProgressScreen(
                gameProgressState = gameProgressState,
                onNavigateCancel = onCancel,
                onEvent = viewModel::onGameProgressEvent,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
            CardChoiceScreen(
                state = cardChoiceState,
                isWinnerScreen = viewModel.isWinnerState() ,
                onEvent = viewModel::onCardChoiceEvent,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
        }
    }
}