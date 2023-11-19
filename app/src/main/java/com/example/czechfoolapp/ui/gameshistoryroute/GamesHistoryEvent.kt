package com.example.czechfoolapp.ui.gameshistoryroute

sealed interface GamesHistoryEvent {
    data class ViewGameDetails(val gameId: Int) : GamesHistoryEvent
    data object StartNewGame : GamesHistoryEvent
    data class ContinueGame(val gameId: Int) : GamesHistoryEvent

}