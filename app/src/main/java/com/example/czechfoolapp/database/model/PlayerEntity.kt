package com.example.czechfoolapp.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "player",
    primaryKeys = ["game_id, player_id"],
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
        Index(value = ["game_id", "player_id"])
    ]
)
data class PlayerEntity(
    @ColumnInfo(name = "game_id")
    val gameID: Int,
    @ColumnInfo(name = "player_id")
    @PrimaryKey(autoGenerate = true)
    val playerID: Int,
    val name: String,
    val score: Int,
)

