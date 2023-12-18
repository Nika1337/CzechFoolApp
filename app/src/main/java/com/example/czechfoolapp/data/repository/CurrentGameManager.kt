package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.model.Player
import kotlinx.coroutines.flow.Flow

interface CurrentGameManager {
    suspend fun setCurrentGame(game: Game)
    fun getCurrentGame(): Flow<Game>
    suspend fun updatePlayer(player: Player)
}