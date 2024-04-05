package com.example.czechfoolapp.core.database.dao

import com.example.czechfoolapp.core.database.model.GameEntity
import com.example.czechfoolapp.core.database.model.PlayerEntity
import java.time.LocalDateTime

object FakeDataSource {
    val game1 =
        GameEntity(
            gameId = 1,
            losingScore = 200,
            date = LocalDateTime.now(),
        )

    val game2 =
        GameEntity(
            gameId = 2,
            losingScore = 300,
            date = LocalDateTime.now(),
        )

    val game1Players = listOf(
        PlayerEntity(
            gameId = game1.gameId,
            playerId = 1,
            name = "Nika",
            score = 0
        ),
        PlayerEntity(
            gameId = game1.gameId,
            playerId = 2,
            name = "Taso",
            score = 102
        ),
        PlayerEntity(
            gameId = game1.gameId,
            playerId = 3,
            name = "Neka",
            score = 75
        )
    )

    val game2Players = listOf(
        PlayerEntity(
            gameId = game2.gameId,
            playerId = 1,
            name = "Nika",
            score = 0
        ),
        PlayerEntity(
            gameId = game2.gameId,
            playerId = 2,
            name = "Taso",
            score = 102
        ),
        PlayerEntity(
            gameId = game2.gameId,
            playerId = 3,
            name = "Neka",
            score = 75
        )
    )
}