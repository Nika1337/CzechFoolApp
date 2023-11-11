package com.example.czechfoolapp.domain.use_case

import com.example.czechfoolapp.data.repository.CurrentPlayerNamesRepository
import com.example.czechfoolapp.ui.nameinputroute.PlayerNameState
import com.example.czechfoolapp.util.toNameList

class StoreCurrentPlayerNamesUseCase(
    private val currentPlayerNamesRepository: CurrentPlayerNamesRepository
) {
    operator fun invoke(players: Map<Int, PlayerNameState>) {
        val currentPlayers = players.toNameList()
        currentPlayerNamesRepository.setPlayerNames(currentPlayers)
    }
}