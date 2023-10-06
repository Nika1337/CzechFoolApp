package com.example.czechfoolapp.ui.gameoptionsroute

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.czechfoolapp.domain.use_case.ValidateNumberOfPlayersUseCase
import com.example.czechfoolapp.domain.use_case.ValidateLosingScoreUseCase
import com.example.czechfoolapp.ui.gameoptionsroute.newstates.GameOptionsState

class GameOptionsViewModel(
    private val validateNumberOfPlayersUseCase: ValidateNumberOfPlayersUseCase, // TODO DI
    private val validateLosingScoreUseCase: ValidateLosingScoreUseCase // TODO DI
) : ViewModel() {
    var gameOptionsState by mutableStateOf(GameOptionsState())
        private set
    fun onEvent(event: GameOptionEvent) {
        when (event) {
            is GameOptionEvent.LosingScoreChanged -> {
                gameOptionsState = gameOptionsState.copy(losingScore = event.losingScore)
            }
            is GameOptionEvent.NumberOfPlayersChanged -> {
                gameOptionsState = gameOptionsState.copy(numberOfPlayers = event.numberOfPlayers)
            }
            is GameOptionEvent.Next -> {
                submitGameOptions()
            }
        }
        gameOptionsState = gameOptionsState.copy(isEverythingValid = false)
    }

    private fun submitGameOptions() {
        val numberOfPlayersResult = validateNumberOfPlayersUseCase.execute(gameOptionsState.numberOfPlayers)
        val losingScoreResult = validateLosingScoreUseCase.execute(gameOptionsState.losingScore)

        val hasError = listOf(
            numberOfPlayersResult,
            losingScoreResult
        ).any { it.successful.not() }

        gameOptionsState = gameOptionsState.copy(
            numberOfPlayersError = numberOfPlayersResult.errorMessage,
            losingScoreError = numberOfPlayersResult.errorMessage
        )
        if (!hasError) {
            gameOptionsState = gameOptionsState.copy(
                isEverythingValid = true
            )
        }
    }
}

