package com.example.czechfoolapp.fake

import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.repository.GamesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeGamesRepository : GamesRepository {
    private var currentGames: MutableList<Game> = mutableListOf()
    private var maxGameID = 0
    override suspend fun insert(game: Game) {
        maxGameID++
        val updatedGame = game.copy(id = maxGameID, isStarted = true)
        currentGames.add(updatedGame)
    }

    override suspend fun delete(game: Game) {
        currentGames.remove(game)
    }

    override suspend fun update(game: Game) {
        currentGames = currentGames.map {
            if (it.id == game.id) {
                game
            } else {
                it
            }
        }.toMutableList()
    }

    override fun getAllGames(): Flow<List<Game>> {
        return flowOf(
            currentGames
        )
    }

    override suspend fun getMaxGameId(): Int {
        return maxGameID
    }
}