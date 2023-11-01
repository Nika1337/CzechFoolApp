package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayersRepository {
    suspend fun insertAll(vararg players: Player)

    suspend fun delete(player: Player)

    suspend fun update(player: Player)

    fun getAllPlayersInGameSpecified(gameID: Int) : Flow<List<Player>>

    suspend fun getPlayer(gameID: Int, name: String) : Player
}