package com.example.czechfoolapp.domain

import com.example.czechfoolapp.data.repository.CurrentGameRepository
import com.example.czechfoolapp.data.repository.CurrentPlayerNamesRepository
import com.example.czechfoolapp.data.repository.PlayersRepository
import com.example.czechfoolapp.ui.nameinputroute.PlayerNameState
import com.example.czechfoolapp.util.toPlayersList

class StartGameAndInsertPlayersUseCase(
    private val currentGameRepository: CurrentGameRepository,
    private val currentPlayerNamesRepository: CurrentPlayerNamesRepository,
    private val playersRepository: PlayersRepository
) {
    suspend operator fun invoke(players: Map<Int, PlayerNameState>) {
        currentPlayerNamesRepository.clearCurrentPlayerNames()
        currentGameRepository.startGame()
        val currentGameId = currentGameRepository.getCurrentGame()!!.id
        val currentPlayers = players.toPlayersList(currentGameId)
        playersRepository.insertAll(*(currentPlayers.toTypedArray()))
    }
}