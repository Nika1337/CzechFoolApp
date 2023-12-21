package com.example.czechfoolapp.data.model

import com.example.czechfoolapp.database.model.PlayerEntity
import com.example.czechfoolapp.util.capitalizeFirstCharacter

data class Player(
    val id: Int,
    val name: String,
    val score: Int = 0
)

fun Player.toPlayerEntity(gameId: Int) =
    PlayerEntity(
        gameId = gameId,
        playerId = this.id,
        name = this.name.capitalizeFirstCharacter(),
        score = this.score
    )
