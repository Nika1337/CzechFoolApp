package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.model.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.forEach

class DefaultCurrentGameManager(
    private val gamesRepository: GamesRepository,
    private val playersRepository: PlayersRepository
) : CurrentGameManager{
    private var currentGameID: Int = -1
    override suspend fun startNewGame(game: Game) {
        gamesRepository.insertWithoutPlayers(game)
        playersRepository.insertAll(
            players = game.players.toTypedArray(),
            gameID = game.id
        )
        currentGameID = game.id
    }

    override suspend fun continueGame(gameID: Int) {
        currentGameID = gameID
    }

    override fun getCurrentGame(): Flow<Game> {
        if (currentGameID == -1) {
            throw IllegalStateException("Game not set")
        }
        return gamesRepository.getGame(currentGameID).filterNotNull()
    }

    override suspend fun updatePlayer(player: Player) {
        playersRepository.update(
            player = player,
            gameID = currentGameID
        )
    }

}