package com.example.czechfoolapp.feature.game.gameprogressroute

sealed interface GameProgressEvent {
    data class PlayerClicked(val id: Int): GameProgressEvent
    data class Cancel(val onNavigateCancel: () -> Unit): GameProgressEvent
    data object Done: GameProgressEvent
}