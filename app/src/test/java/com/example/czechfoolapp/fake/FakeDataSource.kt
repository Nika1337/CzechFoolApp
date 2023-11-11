package com.example.czechfoolapp.fake

import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.model.Player
import java.time.LocalDateTime

object FakeDataSource {
    const val gameId1 = 1
    const val gameId2 = 2
    val maxGameId = gameId2
    val games = listOf(
        Game(
            id = gameId1,
            losingScore = 200,
            numberOfPlayers = 4,
            date = LocalDateTime.now(),
            isStarted = true
        ),
        Game(
            id = gameId2,
            losingScore = 300,
            numberOfPlayers = 3,
            date = LocalDateTime.now(),
            isStarted = true
        )
    )

    val players = listOf(
        Player(
            gameId = gameId1,
            playerId = 1,
            name = "Emily",
        ),
        Player(
            gameId = gameId1,
            playerId = 2,
            name = "Joseph"
        ),
        Player(
            gameId = gameId1,
            playerId = 3,
            name = "Farhad"
        ),
        Player(
            gameId = gameId2,
            playerId = 1,
            name = "James"
        ),
        Player(
            gameId = gameId2,
            playerId = 2,
            name = "Farhad"
        ),
        Player(
            gameId = gameId2,
            playerId = 3,
            name = "Maryam"
        ),
        Player(
            gameId = gameId2,
            playerId = 4,
            name = "Parvati"
        )
    )
}