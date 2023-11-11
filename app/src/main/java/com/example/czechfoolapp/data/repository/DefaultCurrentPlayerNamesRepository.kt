package com.example.czechfoolapp.data.repository


class DefaultCurrentPlayerNamesRepository : CurrentPlayerNamesRepository {
    private var currentPlayers: MutableList<String> = mutableListOf()
    override fun setPlayerNames(players: List<String>) {
        clearCurrentPlayerNames()
        currentPlayers.addAll(players)
    }

    override fun getCurrentPlayerNames(): List<String> = currentPlayers.toList()

    override fun clearCurrentPlayerNames() {
        currentPlayers.clear()
    }
}

