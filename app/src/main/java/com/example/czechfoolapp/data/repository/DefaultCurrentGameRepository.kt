package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Game


class DefaultCurrentGameRepository(
    private val gamesRepository: GamesRepository
) : CurrentGameRepository {
    private var currentGame: Game? = null
    override fun setGame(game: Game) {
        if (currentGame != null) {
            throw IllegalStateException("Game already set")
        }
        currentGame = game
    }

    override fun getCurrentGame(): Game? = currentGame


    override suspend fun startGame() {
        if (currentGame == null) {
            throw IllegalStateException("No game set")
        }
        if (currentGame!!.isStarted) {
            throw IllegalStateException("Game already Started")
        }
        gamesRepository.insert(currentGame!!)
        val gameWithCorrectId = currentGame!!.copy(
            id = gamesRepository.getMaxGameId()
        )
        currentGame = gameWithCorrectId
    }

    override suspend fun updateGame(game: Game) {
        if (currentGame == null) {
            throw IllegalStateException("No game set")
        }
        if (currentGame!!.isStarted) {
            throw IllegalStateException("Started game can't be modified")
        }
        currentGame = game
    }

    override suspend fun cancelGame() {
        if (currentGame == null) {
            throw IllegalStateException("No game set")
        }
        currentGame = null
    }

    override suspend fun endGame() {
        if (currentGame == null) {
            throw IllegalStateException("No game in progress")
        }
        gamesRepository.update(
            currentGame!!.copy(
                isFinished = true
            )
        )
        currentGame = null
    }
}