package com.example.czechfoolapp.core.data.fake

import com.example.czechfoolapp.core.database.dao.GameDao
import com.example.czechfoolapp.core.database.model.GameEntity
import com.example.czechfoolapp.core.database.model.GameWithPlayers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGameDao: GameDao {
    private var games: MutableList<GameEntity> = mutableListOf()
    private var maxGameId = 1
    override suspend fun insert(game: GameEntity): Long {
        return if (game.gameId == 0) {
            games.add(game.copy(gameId = maxGameId++))
            (maxGameId - 1).toLong()
        } else {
            games.add(game)
            if (game.gameId > maxGameId) {
                maxGameId = game.gameId
            }
            game.gameId.toLong()
        }
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

    override suspend fun doesGameExistByID(gameID: Int): Boolean = games.any {
        it.gameId == gameID
    }

    override suspend fun getMaxGameID(): Int = maxGameId

}