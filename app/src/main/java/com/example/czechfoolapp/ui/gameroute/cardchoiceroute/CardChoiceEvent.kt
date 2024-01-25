package com.example.czechfoolapp.ui.gameroute.cardchoiceroute

sealed interface CardChoiceEvent {
    data class CountChanged(val index: Int,val count: Int): CardChoiceEvent
    data object Done: CardChoiceEvent
}