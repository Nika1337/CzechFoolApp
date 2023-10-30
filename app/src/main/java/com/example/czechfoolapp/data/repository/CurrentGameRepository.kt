package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Game

interface CurrentGameRepository{
    suspend fun startGame(game: Game)

    fun getCurrentGame() : Game?

    suspend fun endGame()

}