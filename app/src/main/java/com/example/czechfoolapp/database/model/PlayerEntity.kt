package com.example.czechfoolapp.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.czechfoolapp.data.model.Player

@Entity(
    tableName = "player",
    primaryKeys = [
        "game_id",
        "player_id"
    ],
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["game_id"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["game_id", "name"], unique = true)
    ]
)
data class PlayerEntity(
    @ColumnInfo(name = "game_id")
    val gameId: Int,
    @ColumnInfo(name = "player_id")
    val playerId: Int,
    val name: String,
    val score: Int,
)

fun PlayerEntity.toPlayer() =
    Player(
        gameId = this.gameId,
        playerId = this.playerId,
        name = this.name,
        score = this.score
    )