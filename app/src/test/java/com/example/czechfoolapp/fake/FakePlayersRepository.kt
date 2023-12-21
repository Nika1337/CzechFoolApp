package com.example.czechfoolapp.fake

import com.example.czechfoolapp.data.model.Player
import com.example.czechfoolapp.data.repository.PlayersRepository
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
            }
        }
    }

    override fun getAllPlayersInGameSpecified(gameID: Int): Flow<List<Player>> {
        return flowOf(
            currentPlayers[gameID]!!
        )
    }
}