package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.model.Player
import kotlinx.coroutines.flow.Flow

interface CurrentGameManager {
    fun isGameInProgress() : Boolean
    suspend fun startNewGame(game: Game)

    suspend fun continueGame(gameID: Int)
    fun stopGame()
    fun getCurrentGame(): Flow<Game>
    suspend fun updatePlayer(player: Player)

}