package com.example.czechfoolapp.core.data.repository

import com.example.czechfoolapp.core.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayersRepository {
    suspend fun insertAll(vararg players: Player, gameID: Int)

    suspend fun delete(player: Player, gameID: Int)

    suspend fun update(player: Player, gameID: Int)

    fun getAllPlayersInGameSpecified(gameID: Int) : Flow<List<Player>>
}