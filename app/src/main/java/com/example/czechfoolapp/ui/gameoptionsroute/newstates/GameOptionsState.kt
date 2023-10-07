package com.example.czechfoolapp.ui.gameoptionsroute.newstates

data class GameOptionsState(
    val numberOfPlayersState: GameOptionState = GameOptionState(),
    val losingScoreState: GameOptionState = GameOptionState()
)

data class GameOptionState(
    val value: String = "",
    val errorMessage: String? = null
)
