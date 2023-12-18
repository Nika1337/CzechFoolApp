package com.example.czechfoolapp.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.example.czechfoolapp.data.model.Game

data class GameWithPlayers(
    @Embedded
    val gameEntity: GameEntity,
    @Relation(
        parentColumn = "game_id",
        entityColumn = "game_id"
    )
    val playerEntities: List<PlayerEntity>
)


fun GameWithPlayers.toGame() =
    Game(
        id = this.gameEntity.gameId,
        losingScore = this.gameEntity.losingScore,
        date = this.gameEntity.date,
        players = this.playerEntities.toPlayers()
    )