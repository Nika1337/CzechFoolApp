package com.example.czechfoolapp.ui.gameroute.gameprogressroute

sealed interface GameProgressEvent {
    data class PlayerClicked(val id: Int): GameProgressEvent
    data class Cancel(val onNavigateCancel: () -> Unit): GameProgressEvent
}