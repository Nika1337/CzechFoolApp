package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Player

interface CurrentPlayersRepository {
    fun setPlayers(players: List<Player>)
    fun getCurrentPlayers() : List<Player>
    fun clearCurrentPlayers()
}