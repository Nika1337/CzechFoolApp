package com.example.czechfoolapp.feature.nameinput

import com.example.czechfoolapp.core.data.util.capitalizeFirstCharacter
import com.example.czechfoolapp.feature.nameinput.states.PlayerNameState

internal fun Map<Int, PlayerNameState>.resetNameErrors() =
    this.mapValues { (_: Int, playerNameState: PlayerNameState) ->
        playerNameState.copy(nameError = null)
    }

internal fun Map<Int, PlayerNameState>.capitalizeAndTrim() =
    this.mapValues { (_: Int, playerNameState: PlayerNameState) ->
        playerNameState.copy(
            name = playerNameState.name.trim().capitalizeFirstCharacter()
        )
    }

internal fun Map<Int, PlayerNameState>.getDuplicates(): List<Int> {
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
