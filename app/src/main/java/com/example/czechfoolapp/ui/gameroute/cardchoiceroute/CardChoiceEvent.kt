package com.example.czechfoolapp.ui.gameroute.cardchoiceroute

sealed interface CardChoiceEvent {
    data class CountChangeClicked(val count: Int): CardChoiceEvent
}