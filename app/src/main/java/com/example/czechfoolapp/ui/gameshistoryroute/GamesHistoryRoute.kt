package com.example.czechfoolapp.ui.gameshistoryroute

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.example.czechfoolapp.ui.util.GamesHistoryContentType

@Composable
fun GamesHistoryRoute(
    onStartNewGame: () -> Unit,
    onContinueGame: () -> Unit,
    windowWidth: WindowWidthSizeClass
) {
    val gamesHistoryContentType = when(windowWidth) {
        WindowWidthSizeClass.Compact -> {
            GamesHistoryContentType.GAME_LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            GamesHistoryContentType.GAME_LIST_AND_DETAIL
        }
        WindowWidthSizeClass.Expanded -> {
            GamesHistoryContentType.GAME_LIST_AND_DETAIL
        }
        else -> {
            GamesHistoryContentType.GAME_LIST_ONLY
        }
    }
}