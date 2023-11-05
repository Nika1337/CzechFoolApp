package com.example.czechfoolapp.data.repository

import android.database.sqlite.SQLiteAbortException
import android.database.sqlite.SQLiteConstraintException
import com.example.czechfoolapp.data.model.Player
import com.example.czechfoolapp.data.model.toPlayerEntity
import com.example.czechfoolapp.database.dao.PlayerDao
import com.example.czechfoolapp.database.model.toPlayer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OfflinePlayersRepository(
    private val playerDao: PlayerDao
) : PlayersRepository {
    override suspend fun insertAll(vararg players: Player) = try {
        playerDao.insertAll(*(players.map { it.toPlayerEntity() }.toTypedArray()))
    } catch (e: SQLiteConstraintException) {
        throw IllegalArgumentException("There shouldn't be duplicate names in one game")
    } catch (e: SQLiteAbortException) {
        throw IllegalArgumentException("One of given players already exist in database")
    }

    override suspend fun delete(player: Player) {
        playerDao.delete(player.toPlayerEntity())
    }

    override suspend fun update(player: Player) = try {
        playerDao.update(player.toPlayerEntity())
    } catch (e: SQLiteConstraintException) {
        throw IllegalArgumentException("There shouldn't be duplicate names in one game")
    }

    override fun getAllPlayersInGameSpecified(gameID: Int): Flow<List<Player>> =
        playerDao.getAllPlayersInGameSpecified(gameID)
            .map {
                it.map { playerEntity ->
                    playerEntity.toPlayer()
                }
            }

    override suspend fun getPlayer(gameID: Int, name: String): Player =
        playerDao.getPlayer(gameID = gameID, name = name).toPlayer()
}