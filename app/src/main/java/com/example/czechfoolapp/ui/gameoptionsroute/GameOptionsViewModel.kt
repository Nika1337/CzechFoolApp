package com.example.czechfoolapp.ui.gameoptionsroute

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.czechfoolapp.CzechFoolApplication
import com.example.czechfoolapp.data.DefaultValuesSource
import com.example.czechfoolapp.domain.validation.ValidateLosingScoreUseCase
import com.example.czechfoolapp.domain.validation.ValidateNumberOfPlayersUseCase
import com.example.czechfoolapp.ui.gameoptionsroute.states.GameOptionsState
import kotlinx.coroutines.launch

class GameOptionsViewModel(
    private val validateNumberOfPlayersUseCase: ValidateNumberOfPlayersUseCase,
    private val validateLosingScoreUseCase: ValidateLosingScoreUseCase,
    private val gameBuilder: GameBuilder
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
        viewModelScope.launch {
            val numberOfPlayersResult =
                validateNumberOfPlayersUseCase(gameOptionsState.numberOfPlayersState.value.text)
            val losingScoreResult =
                validateLosingScoreUseCase(gameOptionsState.losingScoreState.value.text)

            val hasError = listOf(
                numberOfPlayersResult,
                losingScoreResult
            ).any { it.successful.not() }

            gameOptionsState = gameOptionsState.copy(
                losingScoreState = gameOptionsState.losingScoreState.copy(errorMessage = losingScoreResult.errorMessage),
                numberOfPlayersState = gameOptionsState.numberOfPlayersState.copy(errorMessage = numberOfPlayersResult.errorMessage)
            )
            if (hasError) {
                return@launch
            }
            saveGameOptions()
            navigateToNext()
        }
    }

    private fun saveGameOptions() {
        gameBuilder
            .setLosingScore(gameOptionsState.losingScoreState.value.text.toInt())
            .setNumberOfPlayers(gameOptionsState.numberOfPlayersState.value.text.toInt())
    }


    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CzechFoolApplication)
                val validateNumberOfPlayersUseCase = application.container.validateNumberOfPlayersUseCase
                val validateLosingScoreUseCase = application.container.validateLosingScoreUseCase
                val gameBuilder = application.container.gameBuilder
                GameOptionsViewModel(
                    validateNumberOfPlayersUseCase = validateNumberOfPlayersUseCase,
                    validateLosingScoreUseCase = validateLosingScoreUseCase,
                    gameBuilder = gameBuilder
                )
            }
        }
    }
}

