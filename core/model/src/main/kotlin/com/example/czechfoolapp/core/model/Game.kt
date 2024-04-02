package com.example.czechfoolapp.core.model

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
        private var players: List<Player> = listOf()

        fun losingScore(losingScore: Int): Builder {
            this.losingScore = losingScore
            return this
        }
        fun players(players: List<Player>): Builder {
            this.players = players
            return this
        }

        fun build(): Game {
            checkNotNull(losingScore) { "Losing score not set" }
            return Game(
                id = 0,
                losingScore = losingScore!!,
                players = players,
                date = LocalDateTime.now()
            )
        }

    }
}

