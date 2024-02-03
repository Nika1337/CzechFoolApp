package com.example.czechfoolapp.ui.routes.gameroute.cardchoiceroute

sealed interface CardChoiceEvent {
    data class CountChanged(val index: Int,val count: Int): CardChoiceEvent
    data object Done: CardChoiceEvent

    data object NavigateUp: CardChoiceEvent
}