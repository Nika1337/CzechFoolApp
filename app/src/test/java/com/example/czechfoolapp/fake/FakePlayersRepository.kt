package com.example.czechfoolapp.fake

import com.example.czechfoolapp.data.model.Player
import com.example.czechfoolapp.data.repository.PlayersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakePlayersRepository : PlayersRepository {
    private val currentPlayers: MutableList<Player> = mutableListOf()
    override suspend fun insertAll(vararg players: Player) {
        currentPlayers.addAll(players)
    }

    override suspend fun delete(player: Player) {
        currentPlayers.remove(player)
    }

    override suspend fun update(player: Player) {
        currentPlayers.forEachIndexed() { index: Int, it: Player ->
            if (it.gameId == player.gameId && it.playerId == player.playerId) {
                currentPlayers[index] = player
            }
        }
    }

    override fun getAllPlayersInGameSpecified(gameID: Int): Flow<List<Player>> {
        val resultList: MutableList<Player> = mutableListOf()
        currentPlayers.forEach {
            if (it.gameId == gameID) {
                resultList.add(it)
            }
        }
        return flowOf(
            resultList
        )
    }
}