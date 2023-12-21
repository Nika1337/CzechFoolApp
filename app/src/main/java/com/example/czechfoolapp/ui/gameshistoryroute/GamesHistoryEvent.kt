package com.example.czechfoolapp.ui.gameshistoryroute

sealed interface GamesHistoryEvent {
    data class ViewGameDetails(val gameId: Int) : GamesHistoryEvent
    data class StartNewGame(
        val onStartNewGameNavigate: () -> Unit
    ) : GamesHistoryEvent
    data class ContinueGame(
        val gameId: Int,
        val onContinueGameNavigate: () -> Unit
    ) : GamesHistoryEvent
    data object DetailScreenNavigateUp : GamesHistoryEvent
}