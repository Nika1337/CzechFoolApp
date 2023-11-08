package com.example.czechfoolapp.util

import com.example.czechfoolapp.data.model.Player
import com.example.czechfoolapp.data.model.hasSameId
import com.example.czechfoolapp.ui.nameinputroute.PlayerNameState

fun List<Player>.updatePlayer(player: Player) = this.map {
    if (it.hasSameId(player)) {
        player
    } else {
        it
    }
}

fun Map<Int, PlayerNameState>.toList(gameID: Int) = this
    .toMap()
    .toList()
    .map { (id: Int, playerNameState: PlayerNameState) ->
        Player(
            gameId = gameID,
            playerId = id,
            name = playerNameState.name
        )
    }
