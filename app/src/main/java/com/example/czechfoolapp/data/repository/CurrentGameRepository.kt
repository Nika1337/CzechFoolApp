package com.example.czechfoolapp.data.repository

import com.example.czechfoolapp.data.model.Game

interface CurrentGameRepository{
    fun setGame(game: Game)
    fun getCurrentGame() : Game?
    suspend fun startGame()
    fun updateGame(game: Game)
    fun cancelGame()
    suspend fun endGame()

}