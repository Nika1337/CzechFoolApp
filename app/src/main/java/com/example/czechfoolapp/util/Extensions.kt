package com.example.czechfoolapp.util

import com.example.czechfoolapp.data.model.Player
import com.example.czechfoolapp.data.model.hasSameId

fun List<Player>.updatePlayer(player: Player) = this.map {
    if (it.hasSameId(player)) {
        player
    } else {
        it
    }
}