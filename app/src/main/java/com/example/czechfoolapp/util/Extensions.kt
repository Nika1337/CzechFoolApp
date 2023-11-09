package com.example.czechfoolapp.util

import com.example.czechfoolapp.data.model.Player
import com.example.czechfoolapp.ui.nameinputroute.PlayerNameState



fun Map<Int, PlayerNameState>.toPlayersList(gameID: Int) = this
    .toMap()
    .toList()
    .map { (id: Int, playerNameState: PlayerNameState) ->
        Player(
            gameId = gameID,
            playerId = id,
            name = playerNameState.name.trim()
        )
    }

fun Map<Int, PlayerNameState>.getDuplicates(): List<Int> {
    val seenValues: MutableList<String> = mutableListOf()
    val duplicateKeys: MutableList<Int> = mutableListOf()
    this.forEach {
        if (seenValues.contains(it.value.name)) {
            duplicateKeys.add(it.key)
        } else {
            seenValues.add(it.value.name)
        }
    }
    return duplicateKeys.toList()
}