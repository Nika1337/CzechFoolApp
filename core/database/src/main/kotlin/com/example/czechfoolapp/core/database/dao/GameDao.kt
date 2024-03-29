package com.example.czechfoolapp.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.czechfoolapp.core.database.model.GameEntity
import com.example.czechfoolapp.core.database.model.GameWithPlayers
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(game: GameEntity)

    @Delete
    suspend fun delete(game: GameEntity)
    @Transaction
    @Query(
        "SELECT * FROM game " +
        "where game_id = :id "
    )
    fun getGame(id: Int): Flow<GameWithPlayers?>
    @Transaction
    @Query(
        "SELECT * FROM game ORDER BY game_id DESC"
    )
    fun getAllGames(): Flow<List<GameWithPlayers>>

    @Query(
        "SELECT MAX(game_id) FROM game"
    )
    suspend fun getMaxGameID(): Int

    @Query("SELECT EXISTS(SELECT * FROM game WHERE game_id = :gameID)")
    suspend fun doesGameExistByID(gameID: Int): Boolean
}