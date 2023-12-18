package com.example.czechfoolapp.fake

import com.example.czechfoolapp.database.dao.GameDao
import com.example.czechfoolapp.database.model.GameEntity
import com.example.czechfoolapp.database.model.GameWithPlayers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGameDao: GameDao {
    private var games: MutableList<GameEntity> = mutableListOf()
    override suspend fun insert(game: GameEntity) {
        games.add(game)
    }

    override suspend fun delete(game: GameEntity) {
        games.remove(game)
    }

    override fun getGame(id: Int): Flow<GameWithPlayers> = flow {
        val gameEntity = games.find {
            it.gameId == id
        } ?: throw IllegalStateException("No such value in Fake Game Dao")
        val gameWithPlayers = GameWithPlayers(
            gameEntity = gameEntity,
            playerEntities = emptyList()
        )
        emit(gameWithPlayers)
    }

    override fun getAllGames(): Flow<List<GameWithPlayers>> = flow {
        val gameWithPlayersList = games.map { gameEntity ->
            GameWithPlayers(
                gameEntity = gameEntity,
                playerEntities = emptyList()
            )
        }
        emit(gameWithPlayersList)
    }
}