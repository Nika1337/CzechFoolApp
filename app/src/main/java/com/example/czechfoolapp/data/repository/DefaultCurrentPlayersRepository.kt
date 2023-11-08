package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Player

class DefaultCurrentPlayersRepository : CurrentPlayersRepository {
    private val currentPlayers: MutableList<Player> = mutableListOf()
    override fun setPlayers(players: List<Player>) {
        clearCurrentPlayers()
        currentPlayers.addAll(players)
    }

    override fun getCurrentPlayers(): List<Player> = currentPlayers.toList()

    override fun clearCurrentPlayers() {
        currentPlayers.clear()
    }
}

