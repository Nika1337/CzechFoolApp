package com.example.czechfoolapp.domain.use_case

import com.example.czechfoolapp.data.repository.CurrentGameRepository
import com.example.czechfoolapp.data.repository.CurrentPlayersRepository
import com.example.czechfoolapp.ui.nameinputroute.PlayerNameState
import com.example.czechfoolapp.util.toList

class SetPlayersAndStartGameUseCase(
    private val currentGameRepository: CurrentGameRepository,
    private val currentPlayersRepository: CurrentPlayersRepository
) {
    suspend operator fun invoke(players: Map<Int, PlayerNameState>) {
        currentGameRepository.startGame()
        val currentGameId = currentGameRepository.getCurrentGame()!!.id
        val currentPlayers = players.toList(currentGameId)
        currentPlayersRepository.setPlayers(currentPlayers)
    }
}