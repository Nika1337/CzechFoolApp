package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Game
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun insert(game: Game)
    suspend fun delete(game: Game)

    suspend fun update(game: Game)
    fun getAllGames() : Flow<List<Game>>
    suspend fun getMaxGameId() : Int
}