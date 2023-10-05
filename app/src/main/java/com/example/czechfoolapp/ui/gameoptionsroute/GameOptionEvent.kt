package com.example.czechfoolapp.ui.gameoptionsroute

sealed interface GameOptionEvent {
    data class NumberOfPlayersChanged(val numberOfPlayers: String) : GameOptionEvent
    data class LosingScoreChanged(val losingScore: String) : GameOptionEvent
    object Next: GameOptionEvent

}