package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Game
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun insertWithoutPlayers(game: Game)
    suspend fun delete(game: Game)
    fun getGame(gameId: Int): Flow<Game?>
    fun getAllGames() : Flow<List<Game>>
}