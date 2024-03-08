package com.example.czechfoolapp.core.data.repository.fake

import com.example.czechfoolapp.core.data.repository.PlayersRepository
import com.example.czechfoolapp.core.model.Player
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakePlayersRepository : PlayersRepository {
    private val currentPlayers: MutableMap<Int,MutableList<Player>> = mutableMapOf()
    override suspend fun insertAll(vararg players: Player, gameID: Int) {
        currentPlayers[gameID] = players.toMutableList()
    }

    override suspend fun delete(player: Player, gameID: Int) {
        currentPlayers[gameID]!!.remove(player)
    }

    override suspend fun update(player: Player, gameID: Int) {
        currentPlayers[gameID]!!.forEachIndexed { index: Int, it: Player ->
            if (it.id == player.id) {
                currentPlayers[gameID]!![index] = player
                return
            }
        }

    }

    override fun getAllPlayersInGameSpecified(gameID: Int): Flow<List<Player>> {
        return flowOf(
            currentPlayers[gameID]!!
        )
    }
}