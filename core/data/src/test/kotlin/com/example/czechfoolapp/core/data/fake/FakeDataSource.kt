package com.example.czechfoolapp.core.data.fake

import com.example.czechfoolapp.core.model.Game
import com.example.czechfoolapp.core.model.Player
import java.time.LocalDateTime

object FakeDataSource {
    const val gameId1 = 1
    const val gameId2 = 2
    const val maxGameID = 2

    val players = mapOf(
        gameId1 to listOf(
            Player(
                id = 1,
                name = "Emily",
            ),
            Player(
                id = 2,
                name = "Joseph"
            ),
            Player(
                id = 3,
                name = "Farhad"
            )
        ),
        gameId2 to listOf(
            Player(
                id = 1,
                name = "James"
            ),
            Player(
                id = 2,
                name = "Farhad"
            ),
            Player(
                id = 3,
                name = "Maryam"
            ),
            Player(
                id = 4,
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