package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Player
import com.example.czechfoolapp.data.model.toPlayerEntity
import com.example.czechfoolapp.database.dao.PlayerDao
import com.example.czechfoolapp.database.model.toPlayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflinePlayersRepository(
    private val playerDao: PlayerDao
) : PlayersRepository {
    override suspend fun insertAll(vararg players: Player) =
        playerDao.insertAll(*(players.map { it.toPlayerEntity() }.toTypedArray()))

    override suspend fun delete(player: Player) {
        playerDao.delete(player.toPlayerEntity())
    }

    override suspend fun update(player: Player) {
        playerDao.update(player.toPlayerEntity())
    }

    override fun getAllPlayersInGameSpecified(gameID: Int): Flow<List<Player>> =
        playerDao.getAllPlayersInGameSpecified(gameID)
            .map {
                it.map { playerEntity ->
                    playerEntity.toPlayer()
                }
            }

    override suspend fun getPlayerStream(gameID: Int, name: String): Player =
        playerDao.getPlayer(gameID = gameID, name = name).toPlayer()
}