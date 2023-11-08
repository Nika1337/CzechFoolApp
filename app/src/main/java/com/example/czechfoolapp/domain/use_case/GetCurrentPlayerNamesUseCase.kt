package com.example.czechfoolapp.domain.use_case

import com.example.czechfoolapp.data.repository.CurrentGameRepository
import com.example.czechfoolapp.data.repository.CurrentPlayersRepository
import com.example.czechfoolapp.ui.nameinputroute.PlayerNameState

class GetCurrentPlayerNamesUseCase(
    private val currentGameRepository: CurrentGameRepository,
    private val currentPlayersRepository: CurrentPlayersRepository
){
    operator fun invoke(): Map<Int, PlayerNameState> {
        val currentGame = currentGameRepository.getCurrentGame()!!
        val currentPlayers = currentPlayersRepository.getCurrentPlayers()
        val newCurrentPlayers: MutableMap<Int, PlayerNameState> = mutableMapOf()
        repeat(currentGame.numberOfPlayers) {
            newCurrentPlayers[it + 1] =
                PlayerNameState(
                    name = if (currentPlayers.size > it) currentPlayers[it].name else ""
                )
        }
        return newCurrentPlayers.toMap()
    }
}