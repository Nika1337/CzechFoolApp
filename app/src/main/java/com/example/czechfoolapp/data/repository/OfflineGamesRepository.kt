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
    override suspend fun insertWithoutPlayers(game: Game) {
        gameDao.insert(game.toGameEntity())
    }

    override suspend fun delete(game: Game) {
        gameDao.delete(game.toGameEntity())
    }

    override fun getGame(gameId: Int): Flow<Game?> =
        gameDao.getGame(gameId).map {
            it?.toGame()
        }



    override fun getAllGames(): Flow<List<Game>> =
        gameDao.getAllGames().map {
            it.map { gameWithPlayers ->
                gameWithPlayers.toGame()
            }
        }

    override suspend fun getMaxGameID() = gameDao.getMaxGameID()
    override suspend fun doesGameExistByID(gameID: Int) = gameDao.doesGameExistByID(gameID)
}










