package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.model.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultCurrentGameManager(
    private val gamesRepository: GamesRepository,
    private val playersRepository: PlayersRepository
) : CurrentGameManager{
    private var currentGameID: Int = -1
    override suspend fun startNewGame(game: Game) {
        if (game.id != 0) {
            throw IllegalArgumentException("New Game ID should always be zero")
        }
        if (currentGameID != -1) {
            throw IllegalStateException("Game already in progress")
        }
        gamesRepository.insertWithoutPlayers(game)
        currentGameID = gamesRepository.getMaxGameID()
        playersRepository.insertAll(
            players = game.players.toTypedArray(),
            gameID = currentGameID
        )
    }

    override suspend fun continueGame(gameID: Int) {
        if (currentGameID != -1) {
            throw IllegalStateException("Game already in progress")
        }
        val doesGameExistByID = gamesRepository.doesGameExistByID(gameID)
        if (doesGameExistByID.not()) {
            throw IllegalArgumentException("No game with given id stored")
        }
        currentGameID = gameID
    }

    override fun stopGame() {
        if (currentGameID == -1) {
            throw IllegalStateException("No game in progress")
        }
        currentGameID = -1
    }

    override fun getCurrentGame(): Flow<Game> {
        if (currentGameID == -1) {
            throw IllegalStateException("No game in progress")
        }
        return gamesRepository.getGame(currentGameID).map {
            it ?: throw IllegalStateException("No game in database with given id")
        }
    }

    override suspend fun updatePlayer(player: Player) {
        if (currentGameID == -1) {
            throw IllegalStateException("No game in progress")
        }
        playersRepository.update(
            player = player,
            gameID = currentGameID
        )
    }

}