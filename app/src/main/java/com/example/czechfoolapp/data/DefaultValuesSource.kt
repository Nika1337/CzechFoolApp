package com.example.czechfoolapp.data

import androidx.compose.ui.text.input.TextFieldValue

object DefaultValuesSource {
    val numbersOfPlayers = listOf(
        TextFieldValue("2"),
        TextFieldValue("3"),
        TextFieldValue("4"),
    )

    val scores = listOf(
        TextFieldValue("200"),
        TextFieldValue("300"),
        TextFieldValue("400"),
    )
    val defaultNumberOfPlayers = numbersOfPlayers[0]
    val defaultScore = scores[0]
}