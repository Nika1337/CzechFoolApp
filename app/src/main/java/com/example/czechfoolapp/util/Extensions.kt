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
            name = playerNameState.name
        )
    }
