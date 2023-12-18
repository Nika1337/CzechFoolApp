package com.example.czechfoolapp.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.czechfoolapp.database.model.PlayerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(vararg players: PlayerEntity)

    @Delete
    suspend fun delete(player: PlayerEntity)

    @Update
    suspend fun update(player: PlayerEntity)

    @Query("SELECT * From player WHERE game_id = :gameID")
    fun getAllPlayersInGameSpecified(gameID: Int) : Flow<List<PlayerEntity>>
}