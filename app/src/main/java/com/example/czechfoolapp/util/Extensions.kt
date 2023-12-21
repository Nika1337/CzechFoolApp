package com.example.czechfoolapp.util

import com.example.czechfoolapp.data.model.Player
import com.example.czechfoolapp.ui.nameinputroute.PlayerNameState
import java.util.Locale


fun Map<Int, PlayerNameState>.toPlayersList() = this
    .toMap()
    .toList()
    .map { (id: Int, playerNameState: PlayerNameState) ->
        Player(
            id = id,
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

fun String.capitalizeFirstCharacter(): String {
    if (this.isEmpty()) {
        return this
    }
    return this.substring(0, 1).uppercase(Locale.ROOT) + this.substring(1).lowercase(Locale.ROOT)
}