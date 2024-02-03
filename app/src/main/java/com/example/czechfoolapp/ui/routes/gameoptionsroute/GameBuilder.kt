package com.example.czechfoolapp.ui.routes.gameoptionsroute

import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.model.Player
import java.time.LocalDateTime


class GameBuilder {
    var losingScore: Int? = null
        private set
    var numberOfPlayers: Int? = null
        private set
    var players: List<Player> = listOf()
        private set
    fun setNumberOfPlayers(numberOfPlayers: Int): GameBuilder {
        this.numberOfPlayers = numberOfPlayers
        return this
    }

    fun setLosingScore(losingScore: Int): GameBuilder {
        this.losingScore = losingScore
        return this
    }
    fun setPlayerNames(players: List<Player>): GameBuilder {
        this.players = players
        return this
    }
    fun reset() {
        losingScore = null
        numberOfPlayers = null
        players = emptyList()
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