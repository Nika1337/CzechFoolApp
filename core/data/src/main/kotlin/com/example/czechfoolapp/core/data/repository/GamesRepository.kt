package com.example.czechfoolapp.core.data.repository

import com.example.czechfoolapp.core.model.Game
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun insertWithoutPlayers(game: Game): Int
    suspend fun delete(game: Game)
    fun getGame(gameId: Int): Flow<Game?>
    fun getAllGames(): Flow<List<Game>>

    suspend fun getMaxGameID(): Int

    suspend fun doesGameExistByID(gameID: Int): Boolean
}