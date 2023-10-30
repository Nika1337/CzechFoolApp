package com.example.czechfoolapp.data.model

import com.example.czechfoolapp.database.model.GameEntity
import java.time.LocalDateTime

data class Game(
    val id: Int,
    val losingScore: Int,
    val numberOfPlayers: Int,
    val date: LocalDateTime
)

fun Game.toGameEntity() =
    GameEntity(
        losingScore = this.losingScore,
        numberOfPlayers = this.numberOfPlayers,
        date = this.date
    )
