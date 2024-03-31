package com.example.czechfoolapp.core.domain

import com.example.czechfoolapp.core.data.repository.GamesRepository
import com.example.czechfoolapp.core.data.repository.PlayersRepository
import com.example.czechfoolapp.core.model.Game
import javax.inject.Inject

class StartNewGameUseCase @Inject constructor(
    private val gamesRepository: GamesRepository,
    private val playersRepository: PlayersRepository
) {
    suspend operator fun invoke(game: Game): Int {
        if (game.id != 0) {
            throw IllegalArgumentException("New Game ID should always be zero")
        }
        val gameId = gamesRepository.insertWithoutPlayers(game)
        playersRepository.insertAll(
            players = game.players.toTypedArray(),
            gameID = gameId
        )
        return gameId
    }
}