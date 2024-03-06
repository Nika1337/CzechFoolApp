package com.example.czechfoolapp.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "game"
)
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    val gameId: Int = 0,
    @ColumnInfo(name = "losing_score")
    val losingScore: Int,
    val date: LocalDateTime
)
