package com.example.czechfoolapp.ui.gameoptionsroute.newstates

data class GameOptionsState(
    val numberOfPlayers: String = "",
    val numberOfPlayersError: String? = null,
    val losingScore: String = "",
    val losingScoreError: String? = null,
    val isEverythingValid: Boolean = false
)
