package com.example.czechfoolapp.ui.gameoptionsroute

import androidx.compose.ui.text.input.TextFieldValue

sealed interface GameOptionEvent {
    data class NumberOfPlayersChanged(val numberOfPlayers: String) : GameOptionEvent
    data class LosingScoreChanged(val losingScore: String) : GameOptionEvent
    data class Next(val navigateToNext: () -> Unit): GameOptionEvent
}