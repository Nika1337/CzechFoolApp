package com.example.czechfoolapp.core.data.repository

import com.example.czechfoolapp.core.data.model.toGameEntity
import com.example.czechfoolapp.core.database.dao.GameDao
import com.example.czechfoolapp.core.model.Game
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineGamesRepository @Inject constructor(
    private val gameDao: GameDao
) : GamesRepository {
    override suspend fun insertWithoutPlayers(game: Game) {
        gameDao.insert(game.toGameEntity())
    }

    override suspend fun delete(game: Game) {
        gameDao.delete(game.toGameEntity())
    }

    override fun getGame(gameId: Int): Flow<Game?> =
        gameDao.getGame(gameId).map {
//            it?.toGame()
            TODO()
        }



    override fun getAllGames(): Flow<List<Game>> =
        gameDao.getAllGames().map {
            it.map { gameWithPlayers ->
//                gameWithPlayers.toGame()
                TODO()
            }
        }

    override suspend fun getMaxGameID() = gameDao.getMaxGameID()
    override suspend fun doesGameExistByID(gameID: Int) = gameDao.doesGameExistByID(gameID)
}










