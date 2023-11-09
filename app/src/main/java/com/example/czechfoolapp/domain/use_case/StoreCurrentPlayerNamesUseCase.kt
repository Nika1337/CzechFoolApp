package com.example.czechfoolapp.domain.use_case

import com.example.czechfoolapp.data.repository.CurrentPlayersRepository
import com.example.czechfoolapp.ui.nameinputroute.PlayerNameState
import com.example.czechfoolapp.util.toPlayersList

class StoreCurrentPlayerNamesUseCase(
    private val currentPlayersRepository: CurrentPlayersRepository
) {
    operator fun invoke(players: Map<Int, PlayerNameState>) {
        val currentPlayers = players.toPlayersList(0)
        currentPlayersRepository.setPlayers(currentPlayers)
    }
}