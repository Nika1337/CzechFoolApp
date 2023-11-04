package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.model.Player
import kotlinx.coroutines.flow.StateFlow

interface CurrentGameRepository{
    suspend fun startGame(game: Game)

    fun getCurrentGame() : StateFlow<Game?>

    suspend fun updateGame(game: Game)

    suspend fun endGame()

}