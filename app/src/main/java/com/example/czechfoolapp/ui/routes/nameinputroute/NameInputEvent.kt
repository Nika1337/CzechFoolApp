package com.example.czechfoolapp.ui.routes.nameinputroute

sealed interface NameInputEvent {
    data class PlayerNameChanged(val id: Int, val value: String) : NameInputEvent
    data class Next(val navigateToNext: () -> Unit) : NameInputEvent
}