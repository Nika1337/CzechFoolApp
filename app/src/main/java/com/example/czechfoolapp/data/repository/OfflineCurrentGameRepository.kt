package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Game

class OfflineCurrentGameRepository(
    private val gamesRepository: GamesRepository
) : CurrentGameRepository {
    private var currentGame: Game? = null
    override suspend fun startGame(game: Game) {
        if (currentGame != null) {
            return
        }
        gamesRepository.insert(game)
        currentGame = game
    }

    override fun getCurrentGame(): Game? {
        return currentGame
    }

    override suspend fun updateGame(game: Game) {
        if (currentGame != null) {
            return
        }
        gamesRepository.update(game)
        currentGame = game
    }

    override suspend fun endGame() {
        currentGame = null
    }
}