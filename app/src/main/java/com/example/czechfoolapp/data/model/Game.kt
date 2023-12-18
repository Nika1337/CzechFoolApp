package com.example.czechfoolapp.data.model

import com.example.czechfoolapp.database.model.GameEntity
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
}

fun Game.toGameEntity() =
    GameEntity(
        gameId = this.id,
        losingScore = this.losingScore,
        date = this.date,
    )
