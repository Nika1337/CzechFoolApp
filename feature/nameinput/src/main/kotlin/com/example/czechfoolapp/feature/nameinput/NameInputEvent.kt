package com.example.czechfoolapp.feature.nameinput

sealed interface NameInputEvent {
    data class PlayerNameChanged(val id: Int, val value: String) : NameInputEvent
    data class Next(val navigateToNext: () -> Unit) : NameInputEvent
}