package com.example.czechfoolapp.data

import androidx.compose.ui.text.input.TextFieldValue

object DefaultValuesSource {
    val numbersOfPlayers = listOf(
        "2",
        "3",
        "4",
    )

    val scores = listOf(
        "200",
        "300",
        "400",
    )
    val defaultNumberOfPlayers = numbersOfPlayers[0]
    val defaultScore = scores[0]
}