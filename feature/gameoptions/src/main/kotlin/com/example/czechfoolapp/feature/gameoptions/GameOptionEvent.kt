package com.example.czechfoolapp.feature.gameoptions


sealed interface GameOptionEvent {
    data class NumberOfPlayersChanged(val numberOfPlayers: String) : GameOptionEvent
    data class LosingScoreChanged(val losingScore: String) : GameOptionEvent
    data class Next(val navigateToNext: (Int, Int) -> Unit): GameOptionEvent
}