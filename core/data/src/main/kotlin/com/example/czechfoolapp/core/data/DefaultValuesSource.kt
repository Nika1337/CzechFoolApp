package com.example.czechfoolapp.data

import javax.inject.Inject


class DefaultValuesSource @Inject constructor() {
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