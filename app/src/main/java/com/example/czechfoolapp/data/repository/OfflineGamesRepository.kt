package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.model.toGameEntity
import com.example.czechfoolapp.database.dao.GameDao
import com.example.czechfoolapp.database.model.toGame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflineGamesRepository(
    private val gameDao: GameDao
) : GamesRepository{
    override suspend fun insert(game: Game) {
        gameDao.insert(game.toGameEntity())
    }

    override suspend fun delete(game: Game) {
        gameDao.delete(game.toGameEntity())
    }

    override suspend fun update(game: Game) {
        gameDao.update(game.toGameEntity())
    }

    override fun getAllGames(): Flow<List<Game>> =
        gameDao.getAllGames()
            .map { gameEntityList ->
                gameEntityList.map { gameEntity ->
                    gameEntity.toGame()
                }
            }

    override suspend fun getMaxGameId(): Int =
        gameDao.getMaxGameId()

}