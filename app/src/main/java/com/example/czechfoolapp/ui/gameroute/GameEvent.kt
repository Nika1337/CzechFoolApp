package com.example.czechfoolapp.ui.gameroute

sealed interface GameEvent {
    data class PlayerClicked(val id: Int): GameEvent
    data class Cancel(val onNavigateCancel: () -> Unit): GameEvent
}