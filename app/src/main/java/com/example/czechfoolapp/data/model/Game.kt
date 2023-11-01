package com.example.czechfoolapp.data.model

import com.example.czechfoolapp.database.model.GameEntity
import java.time.LocalDateTime

data class Game(
    /**
     * When id isn't equal to 0, autogenerate won't happen,
     * user set values are needed only for testing FOR NOW
     */
    val id: Int = 0,
    val losingScore: Int = 0,
    val numberOfPlayers: Int,
    val date: LocalDateTime
)

fun Game.toGameEntity() =
    GameEntity(
        id = this.id,
        losingScore = this.losingScore,
        numberOfPlayers = this.numberOfPlayers,
        date = this.date
    )
