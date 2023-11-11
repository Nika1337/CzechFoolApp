package com.example.czechfoolapp.domain

import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.example.czechfoolapp.data.repository.CurrentGameRepository
import com.example.czechfoolapp.data.repository.CurrentPlayerNamesRepository
import com.example.czechfoolapp.ui.nameinputroute.PlayerNameState

class PopulatePlayerNameStateUseCase(
    private val currentGameRepository: CurrentGameRepository,
    private val currentPlayerNamesRepository: CurrentPlayerNamesRepository
){
    operator fun invoke(playerNameState: SnapshotStateMap<Int, PlayerNameState>) {
        val currentGame = currentGameRepository.getCurrentGame()!!
        val currentPlayerNames = currentPlayerNamesRepository.getCurrentPlayerNames()
        playerNameState.clear()
        repeat(currentGame.numberOfPlayers) {
            playerNameState[it + 1] =
                PlayerNameState(
                    name = if (currentPlayerNames.size > it) currentPlayerNames[it] else ""
                )
        }
    }
}