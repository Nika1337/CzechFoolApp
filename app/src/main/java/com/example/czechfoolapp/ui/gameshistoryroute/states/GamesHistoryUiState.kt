package com.example.czechfoolapp.ui.gameshistoryroute.states

import java.time.LocalDateTime

data class PlayerUiModel(
    val name: String,
    val score: Int
)

data class GameUiModel(
    val gameId: Int,
    val losingScore: Int,
    val date: LocalDateTime,
    val isFinished: Boolean,
    val players: List<PlayerUiModel>
)

data class GamesHistoryUiState(
    val games: List<GameUiModel>
)
