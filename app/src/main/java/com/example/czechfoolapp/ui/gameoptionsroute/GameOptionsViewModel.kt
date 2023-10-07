package com.example.czechfoolapp.ui.gameoptionsroute

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.example.czechfoolapp.data.DefaultValuesSource
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
                Log.d("losingScore", "losing score changed ${gameOptionsState.losingScoreState.value.text}")
            }
            is GameOptionEvent.NumberOfPlayersChanged -> {
                gameOptionsState = gameOptionsState.copy(numberOfPlayersState = gameOptionsState.numberOfPlayersState.copy(value = event.numberOfPlayers))
                Log.d("losingScore", "number of players changed ${gameOptionsState.losingScoreState.value.text}")
            }
            is GameOptionEvent.Next -> {
                submitGameOptions(navigateToNext = event.navigateToNext)
            }
        }
    }
    init {
        gameOptionsState = gameOptionsState.copy(
            numberOfPlayersState = gameOptionsState.numberOfPlayersState.copy(value = DefaultValuesSource.defaultNumberOfPlayers),
            losingScoreState = gameOptionsState.losingScoreState.copy(value = DefaultValuesSource.defaultScore)
        )
    }

    private fun submitGameOptions(navigateToNext: () -> Unit) {
        val numberOfPlayersResult = validateNumberOfPlayersUseCase.execute(gameOptionsState.numberOfPlayersState.value.text)
        val losingScoreResult = validateLosingScoreUseCase.execute(gameOptionsState.losingScoreState.value.text)

        val hasError = listOf(
            numberOfPlayersResult,
            losingScoreResult
        ).any { it.successful.not() }

        gameOptionsState = gameOptionsState.copy(
            losingScoreState = gameOptionsState.losingScoreState.copy(errorMessage = losingScoreResult.errorMessage),
            numberOfPlayersState = gameOptionsState.numberOfPlayersState.copy(errorMessage = numberOfPlayersResult.errorMessage)
        )
        if (hasError) {
            return
        }
        navigateToNext()
    }

}

