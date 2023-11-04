package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Game
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OfflineCurrentGameRepository(
    private val gamesRepository: GamesRepository
) : CurrentGameRepository {
    private val currentGame: MutableStateFlow<Game?> = MutableStateFlow(null)
    override suspend fun startGame(game: Game) {
        if (currentGame.value != null) {
            throw IllegalStateException("Game already in progress")
        }
        gamesRepository.insert(game)
        currentGame.value = game
    }

    override fun getCurrentGame(): StateFlow<Game?> = currentGame

    override suspend fun updateGame(game: Game) {
        if (currentGame.value == null) {
            throw IllegalStateException("No game in progress")
        }
        gamesRepository.update(game)
        currentGame.value = game
    }

    override suspend fun endGame() {
        if (currentGame.value == null) {
            throw IllegalStateException("No game in progress")
        }
        currentGame.value = null
    }
}