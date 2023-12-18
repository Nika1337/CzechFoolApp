package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Player
import com.example.czechfoolapp.data.model.toPlayerEntity
import com.example.czechfoolapp.database.dao.PlayerDao
import com.example.czechfoolapp.database.model.toPlayers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflinePlayersRepository(
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
                it.toPlayers()
            }


}