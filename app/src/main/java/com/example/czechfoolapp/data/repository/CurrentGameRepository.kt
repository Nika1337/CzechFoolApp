package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Game

interface CurrentGameRepository{
    fun setGame(game: Game)
    fun getCurrentGame() : Game?
    suspend fun startGame()
    suspend fun updateGame(game: Game)
    suspend fun cancelGame()
    suspend fun endGame()

}