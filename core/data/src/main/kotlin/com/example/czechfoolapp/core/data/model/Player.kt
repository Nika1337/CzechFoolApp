package com.example.czechfoolapp.core.data.model

import com.example.czechfoolapp.core.data.util.capitalizeFirstCharacter
import com.example.czechfoolapp.core.database.model.PlayerEntity
import com.example.czechfoolapp.core.model.Player

fun Player.toPlayerEntity(gameId: Int) =
    PlayerEntity(
        gameId = gameId,
        playerId = this.id,
        name = this.name.capitalizeFirstCharacter(),
        score = this.score
    )

