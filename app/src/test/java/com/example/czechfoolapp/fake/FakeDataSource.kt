package com.example.czechfoolapp.fake

import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.model.Player
import java.time.LocalDateTime

object FakeDataSource {
    val gameId1 = 1
    val gameId2 = 2
    val games = listOf(
        Game(
            id = gameId1,
            losingScore = 200,
            numberOfPlayers = 4,
            date = LocalDateTime.now()
        ),
        Game(
            id = gameId2,
            losingScore = 300,
            numberOfPlayers = 3,
            date = LocalDateTime.now()
        )
    )

    val players = listOf(
        Player(
            gameId = gameId1,
            name = "Emily",
        ),
        Player(
            gameId = gameId1,
            name = "Joseph"
        ),
        Player(
            gameId = gameId1,
            name = "Farhad"
        ),
        Player(
            gameId = gameId2,
            name = "James"
        ),
        Player(
            gameId = gameId2,
            name = "Farhad"
        ),
        Player(
            gameId = gameId2,
            name = "Maryam"
        ),
        Player(
            gameId = gameId2,
            name = "Parvati"
        )
    )
}