package com.example.czechfoolapp.data.model

import com.example.czechfoolapp.database.model.GameEntity
import java.time.LocalDateTime

data class Game(
    /**
     * When id isn't equal to 0, autogenerate won't happen,
     * user set values are needed only for testing FOR NOW
     */
    val id: Int = 0,
    val losingScore: Int,
    val date: LocalDateTime,
    val players: List<Player>
) {
    val isFinished = players.any { it.score >= losingScore }
    class Builder {
        private var losingScore: Int? = null
        private var numberOfPlayers: Int? = null
        private var players: List<Player> = listOf()
        fun numberOfPlayers(numberOfPlayers: Int): Builder {
            this.numberOfPlayers = numberOfPlayers
            return this
        }

        fun losingScore(losingScore: Int): Builder {
            this.losingScore = losingScore
            return this
        }
        fun players(players: List<Player>): Builder {
            this.players = players
            return this
        }

        fun build(): Game {
            if (losingScore == null) {
                throw IllegalStateException("Losing score not set")
            }
            if (numberOfPlayers == null) {
                throw IllegalStateException("Number of players not set")
            }
            if (players.size != numberOfPlayers) {
                throw IllegalStateException("Number of players variable and number of player list size don't match")
            }
            return Game(
                id = 0,
                losingScore = losingScore!!,
                players = players,
                date = LocalDateTime.now()
            )
        }

    }
}

fun Game.toGameEntity() =
    GameEntity(
        gameId = this.id,
        losingScore = this.losingScore,
        date = this.date,
    )
