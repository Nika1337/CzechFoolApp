package com.example.czechfoolapp.core.data.repository.fake

import com.example.czechfoolapp.core.data.repository.GamesRepository
import com.example.czechfoolapp.core.model.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGamesRepository() : GamesRepository {
    private var currentGames: MutableList<Game> = mutableListOf()
    private var maxGameID = 0
    override suspend fun insertWithoutPlayers(game: Game): Int {
        maxGameID++
        val updatedGame = game.copy(id = maxGameID)
        currentGames.add(updatedGame)
        return maxGameID
    }

    override suspend fun delete(game: Game) {
        currentGames.remove(game)
    }

    override fun getGame(gameId: Int): Flow<Game?> = flowOf(currentGames.find { it.id == gameId })

    override fun getAllGames(): Flow<List<Game>> {
        return flowOf(
            currentGames
        )
    }

    override suspend fun getMaxGameID(): Int = maxGameID
    override suspend fun doesGameExistByID(gameID: Int): Boolean = currentGames.any {
        it.id == gameID
    }

}