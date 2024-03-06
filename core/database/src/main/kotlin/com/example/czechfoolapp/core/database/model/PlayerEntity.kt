package com.example.czechfoolapp.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

//import com.example.czechfoolapp.data.model.Player

@Entity(
    tableName = "player",
    primaryKeys = [
        "game_id",
        "player_id"
    ],
    foreignKeys = [
        ForeignKey(
            entity = GameEntity::class,
            parentColumns = ["game_id"],
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

//fun List<PlayerEntity>.toPlayers() =
//    this.map {
//        Player(
//            id = it.playerId,
//            name = it.name,
//            score = it.score
//        )
//    }