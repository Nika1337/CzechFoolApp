package com.example.czechfoolapp.ui.routes.gameroute

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.czechfoolapp.ui.routes.gameroute.cardchoiceroute.CardChoiceEvent
import com.example.czechfoolapp.ui.routes.gameroute.cardchoiceroute.CardChoiceScreen
import com.example.czechfoolapp.ui.routes.gameroute.gameprogressroute.GameProgressScreen
import com.example.czechfoolapp.ui.routes.gameroute.util.GameContentType
import com.example.czechfoolapp.ui.routes.gameroute.util.GameCurrentScreen

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

    val viewModel: GameViewModel = hiltViewModel()
    val currentScreen by viewModel.currentScreenFlow.collectAsStateWithLifecycle()
    val gameProgressState by viewModel.gameProgressState.collectAsStateWithLifecycle()
    val cardChoiceState by viewModel.cardChoiceState.collectAsStateWithLifecycle()


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
                isWinnerScreen = viewModel.isCandidateState(),
                onEvent = viewModel::onCardChoiceEvent,
                onNavigateUp = { viewModel.onCardChoiceEvent(CardChoiceEvent.NavigateUp) },
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    } else {
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
                isWinnerScreen = viewModel.isCandidateState() ,
                onEvent = viewModel::onCardChoiceEvent,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            )
        }
    }
}