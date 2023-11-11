package com.example.czechfoolapp.data.repository


interface CurrentPlayerNamesRepository {
    fun setPlayerNames(players: List<String>)
    fun getCurrentPlayerNames() : List<String>
    fun clearCurrentPlayerNames()
}