package com.example.czechfoolapp.core.data.repository

import com.example.czechfoolapp.core.data.model.toPlayerEntity
import com.example.czechfoolapp.core.database.dao.PlayerDao
import com.example.czechfoolapp.core.model.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflinePlayersRepository @Inject constructor(
    private val playerDao: PlayerDao
) : PlayersRepository {
    override suspend fun insertAll(vararg players: Player, gameID: Int) =
        playerDao.insertAll(*(players.map { it.toPlayerEntity(gameID) }.toTypedArray()))

    override suspend fun delete(player: Player, gameID: Int) {
        playerDao.delete(player.toPlayerEntity(gameID))
    }

    override suspend fun update(player: Player, gameID: Int) =
        playerDao.update(player.toPlayerEntity(gameID))

    override fun getAllPlayersInGameSpecified(gameID: Int): Flow<List<Player>> =
        playerDao.getAllPlayersInGameSpecified(gameID)
            .map {
//                it.toPlayers()
                TODO()
            }


}