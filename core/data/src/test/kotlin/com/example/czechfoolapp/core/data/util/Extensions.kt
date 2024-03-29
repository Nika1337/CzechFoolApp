package com.example.czechfoolapp.core.data.util

import com.example.czechfoolapp.core.model.Game


fun Game.clearPlayers() = this.copy(players = emptyList())