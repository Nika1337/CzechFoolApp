package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Player
import com.example.czechfoolapp.util.updatePlayer

class DefaultCurrentPlayersRepository(
    private val playersRepository: PlayersRepository
) : CurrentPlayersRepository {
    private var currentPlayers: List<Player> = emptyList()
    override suspend fun setPlayers(players: List<Player>) {
        playersRepository.insertAll(*(players.toTypedArray()))
        currentPlayers = players
    }

    override fun getCurrentPlayers(): List<Player> = currentPlayers.toList()

    override suspend fun updatePlayer(player: Player) {
        currentPlayers.updatePlayer(player)
        playersRepository.update(player)
    }

    override suspend fun clearCurrentPlayers(player: Player) {
        currentPlayers = emptyList()
    }

}

