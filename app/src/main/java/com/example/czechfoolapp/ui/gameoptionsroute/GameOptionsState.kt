package com.example.czechfoolapp.ui.gameoptionsroute

import androidx.compose.ui.text.input.TextFieldValue

data class GameOptionsState(
    val numberOfPlayersState: GameOptionState = GameOptionState(),
    val losingScoreState: GameOptionState = GameOptionState()
)

data class GameOptionState(
    val value: TextFieldValue = TextFieldValue(text = ""),
    val errorMessage: String? = null
)
