package com.example.czechfoolapp.fake

import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.model.Player
import java.time.LocalDateTime

object FakeDataSource {
    const val gameId1 = 1
    const val gameId2 = 2

    val players = mapOf(
        gameId1 to listOf(
            Player(
                playerId = 1,
                name = "Emily",
            ),
            Player(
                playerId = 2,
                name = "Joseph"
            ),
            Player(
                playerId = 3,
                name = "Farhad"
            )
        ),
        gameId2 to listOf(
            Player(
                playerId = 1,
                name = "James"
            ),
            Player(
                playerId = 2,
                name = "Farhad"
            ),
            Player(
                playerId = 3,
                name = "Maryam"
            ),
            Player(
                playerId = 4,
                name = "Parvati"
            )
        )
    )
    val games = listOf(
        Game(
            id = gameId1,
            losingScore = 200,
            date = LocalDateTime.now(),
            players = players[gameId1]!!
        ),
        Game(
            id = gameId2,
            losingScore = 300,
            date = LocalDateTime.now(),
            players = players[gameId2]!!
        )
    )


}