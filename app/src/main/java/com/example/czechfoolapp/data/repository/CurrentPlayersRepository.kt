package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Player

interface CurrentPlayersRepository {
    suspend fun setPlayers(players: List<Player>)
    fun getCurrentPlayers() : List<Player>
    suspend fun updatePlayer(player: Player)
    suspend fun clearCurrentPlayers(player: Player)
}