package com.example.czechfoolapp.core.data.repository

import com.example.czechfoolapp.core.model.Game
import com.example.czechfoolapp.core.model.Player
import kotlinx.coroutines.flow.Flow

interface CurrentGameManager {
    fun isGameInProgress() : Boolean
    suspend fun startNewGame(game: Game)

    suspend fun continueGame(gameID: Int)
    suspend fun stopGame()
    fun getCurrentGameFlow(): Flow<Game>
    suspend fun updatePlayer(player: Player)
    suspend fun restoreLastSavedGame()

}