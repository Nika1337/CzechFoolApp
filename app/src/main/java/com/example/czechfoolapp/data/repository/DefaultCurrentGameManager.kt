package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.model.Player
import kotlinx.coroutines.flow.Flow

class DefaultCurrentGameManager(
    private val gamesRepository: GamesRepository,
    private val playersRepository: PlayersRepository
) : CurrentGameManager{
    private var currentGameID: Int = -1
    override suspend fun setCurrentGame(game: Game) {
        gamesRepository.insertWithoutPlayers(game)
        playersRepository.insertAll(
            players = game.players.toTypedArray(),
            gameID = game.id
        )
        currentGameID = game.id
    }

    override fun getCurrentGame(): Flow<Game> {
        if (currentGameID == -1) {
            throw IllegalStateException("Game not set")
        }
        return gamesRepository.getGame(currentGameID)
    }

    override suspend fun updatePlayer(player: Player) {
        playersRepository.update(
            player = player,
            gameID = currentGameID
        )
    }

}