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
        "name"
    ],
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["id"],
            childColumns = ["game_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["game_id", "name"])
    ]
)
data class PlayerEntity(
    @ColumnInfo(name = "game_id")
    val gameId: Int,
    val name: String,
    val score: Int,
)

fun PlayerEntity.toPlayer() =
    Player(
        gameId = this.gameId,
        name = this.name,
        score = this.score
    )