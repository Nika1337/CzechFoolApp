package com.example.czechfoolapp.data.model

import com.example.czechfoolapp.database.model.PlayerEntity

data class Player(
    val gameId: Int,
    val name: String,
    val score: Int
)

fun Player.toPlayerEntity() =
    PlayerEntity(
        gameId = this.gameId,
        name = this.name,
        score = this.score
    )
