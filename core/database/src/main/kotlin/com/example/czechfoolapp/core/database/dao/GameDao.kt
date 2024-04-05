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
    suspend fun insert(game: GameEntity): Long

    @Delete
    suspend fun delete(game: GameEntity)
    @Transaction
    @Query(
        """
            SELECT *
            FROM game
            WHERE game.game_id = :id
        """
    )
    fun getGame(id: Int): Flow<GameWithPlayers?>
    @Transaction
    @Query(
        """
            SELECT *
            FROM game
        """
    )
    fun getAllGames(): Flow<List<GameWithPlayers>>
}










