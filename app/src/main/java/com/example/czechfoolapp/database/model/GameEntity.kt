package com.example.czechfoolapp.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.czechfoolapp.data.model.Game
import java.time.LocalDateTime

@Entity(
    tableName = "game"
)
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "losing_score")
    val losingScore: Int,
    @ColumnInfo(name = "number_of_players")
    val numberOfPlayers: Int,
    val date: LocalDateTime
)

fun GameEntity.toGame() =
    Game(
        id = this.id,
        losingScore = this.losingScore,
        numberOfPlayers = this.numberOfPlayers,
        date = this.date
    )