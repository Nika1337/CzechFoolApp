package com.example.czechfoolapp.util

import com.example.czechfoolapp.data.model.Game

fun Game.clearPlayers() = this.copy(players = emptyList())