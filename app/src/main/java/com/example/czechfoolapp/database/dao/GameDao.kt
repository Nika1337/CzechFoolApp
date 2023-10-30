package com.example.czechfoolapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.czechfoolapp.database.model.GameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(game: GameEntity)

    @Delete
    suspend fun delete(game: GameEntity)

    @Update
    suspend fun update(game: GameEntity)

    @Query("Select * FROM game")
    fun getAllGames(): Flow<List<GameEntity>>
}