package com.example.czechfoolapp.data.model

import com.example.czechfoolapp.database.model.PlayerEntity

data class Player(
    val gameId: Int,
    val playerId: Int,
    val name: String,
    val score: Int = 0
)

fun Player.toPlayerEntity() =
    PlayerEntity(
        gameId = this.gameId,
        playerId = this.playerId,
        name = this.name,
        score = this.score
    )
