package com.example.czechfoolapp.ui.routes.gameoptionsroute


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.czechfoolapp.CzechFoolApplication
import com.example.czechfoolapp.data.DefaultValuesSource
import com.example.czechfoolapp.domain.validation.ValidateLosingScoreUseCase
import com.example.czechfoolapp.domain.validation.ValidateNumberOfPlayersUseCase
import com.example.czechfoolapp.ui.routes.gameoptionsroute.states.GameOptionState
import com.example.czechfoolapp.ui.routes.gameoptionsroute.states.GameOptionsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val GAME_OPTIONS_STATE = "gameOptionsStateID"

@HiltViewModel
class GameOptionsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val validateNumberOfPlayersUseCase: ValidateNumberOfPlayersUseCase,
    private val validateLosingScoreUseCase: ValidateLosingScoreUseCase,
    private val defaultValuesSource: DefaultValuesSource
) : ViewModel() {
    private val defaultGameOptionsState =
        GameOptionsState(
            numberOfPlayersState = GameOptionState(value = defaultValuesSource.defaultNumberOfPlayers),
            losingScoreState = GameOptionState(value = defaultValuesSource.defaultScore)
        )
    val gameOptionsState = savedStateHandle.getStateFlow(GAME_OPTIONS_STATE, defaultGameOptionsState)
    fun onEvent(event: GameOptionEvent) {
        when (event) {
            is GameOptionEvent.LosingScoreChanged -> {
                savedStateHandle[GAME_OPTIONS_STATE] = gameOptionsState.value.copy(losingScoreState = gameOptionsState.value.losingScoreState.copy(value = event.losingScore))
            }
            is GameOptionEvent.NumberOfPlayersChanged -> {
                savedStateHandle[GAME_OPTIONS_STATE] = gameOptionsState.value.copy(numberOfPlayersState = gameOptionsState.value.numberOfPlayersState.copy(value = event.numberOfPlayers))
            }
            is GameOptionEvent.Next -> {
                submitGameOptions(navigateToNext = event.navigateToNext)
            }
        }
    }

    private fun submitGameOptions(navigateToNext: (Int, Int) -> Unit) {
        viewModelScope.launch {
            val numberOfPlayersResult =
                validateNumberOfPlayersUseCase(gameOptionsState.value.numberOfPlayersState.value)
            val losingScoreResult =
                validateLosingScoreUseCase(gameOptionsState.value.losingScoreState.value)

            val hasError = listOf(
                numberOfPlayersResult,
                losingScoreResult
            ).any { it.successful.not() }

            savedStateHandle[GAME_OPTIONS_STATE] = gameOptionsState.value.copy(
                losingScoreState = gameOptionsState.value.losingScoreState.copy(errorMessage = losingScoreResult.errorMessage),
                numberOfPlayersState = gameOptionsState.value.numberOfPlayersState.copy(errorMessage = numberOfPlayersResult.errorMessage)
            )
            if (hasError) {
                return@launch
            }
            navigateToNext(
                gameOptionsState.value.losingScoreState.value.toInt(),
                gameOptionsState.value.numberOfPlayersState.value.toInt()
            )
        }
    }
}

