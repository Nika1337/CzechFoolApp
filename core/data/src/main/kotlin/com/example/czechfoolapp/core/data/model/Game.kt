package com.example.czechfoolapp.core.data.model

import com.example.czechfoolapp.core.database.model.GameEntity
import com.example.czechfoolapp.core.model.Game

fun Game.toGameEntity() =
    GameEntity(
        gameId = this.id,
        losingScore = this.losingScore,
        date = this.date,
    )
