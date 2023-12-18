package com.example.czechfoolapp.ui.gameoptionsroute

import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.model.Player
import java.time.LocalDateTime


class GameBuilder {
    private var losingScore: Int? = null
    private var numberOfPlayers: Int? = null
    private var players: List<Player> = listOf()

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

    fun build(): Game {
        if (losingScore == null) {
            throw IllegalStateException("Losing score not set")
        }
        if (numberOfPlayers == null) {
            throw IllegalStateException("Number of players not set")
        }
        return Game(
            id = 0,
            losingScore = losingScore!!,
            players = players,
            date = LocalDateTime.now()
        )
    }

}