package com.example.czechfoolapp.ui.gameoptionsroute

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.czechfoolapp.domain.use_case.ValidateNumberOfPlayersUseCase
import com.example.czechfoolapp.domain.use_case.ValidateLosingScoreUseCase
import com.example.czechfoolapp.ui.gameoptionsroute.newstates.GameOptionsState

class GameOptionsViewModel(
    private val validateNumberOfPlayersUseCase: ValidateNumberOfPlayersUseCase = ValidateNumberOfPlayersUseCase(), // TODO DI
    private val validateLosingScoreUseCase: ValidateLosingScoreUseCase = ValidateLosingScoreUseCase() // TODO DI
) : ViewModel() {
    var gameOptionsState by mutableStateOf(GameOptionsState())
        private set
    fun onEvent(event: GameOptionEvent) {
        when (event) {
            is GameOptionEvent.LosingScoreChanged -> {
                gameOptionsState = gameOptionsState.copy(losingScoreState = gameOptionsState.losingScoreState.copy(value = event.losingScore))
            }
            is GameOptionEvent.NumberOfPlayersChanged -> {
                gameOptionsState = gameOptionsState.copy(numberOfPlayersState = gameOptionsState.numberOfPlayersState.copy(value = event.numberOfPlayers))
            }
            is GameOptionEvent.Next -> {
                submitGameOptions()
            }
        }
        gameOptionsState = gameOptionsState.copy(canNavigateNext = false)
    }

    private fun submitGameOptions() {
        val numberOfPlayersResult = validateNumberOfPlayersUseCase.execute(gameOptionsState.numberOfPlayersState.value)
        val losingScoreResult = validateLosingScoreUseCase.execute(gameOptionsState.losingScoreState.value)

        val hasError = listOf(
            numberOfPlayersResult,
            losingScoreResult
        ).any { it.successful.not() }

        gameOptionsState = gameOptionsState.copy(
            losingScoreState = gameOptionsState.losingScoreState.copy(errorMessage = losingScoreResult.errorMessage),
            numberOfPlayersState = gameOptionsState.numberOfPlayersState.copy(errorMessage = losingScoreResult.errorMessage)
        )
        if (!hasError) {
            gameOptionsState = gameOptionsState.copy(
                canNavigateNext = true
            )
        }
    }


}

