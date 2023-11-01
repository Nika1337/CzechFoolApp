package com.example.czechfoolapp.ui.gameoptionsroute

import android.util.Log
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
import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.repository.CurrentGameRepository
import com.example.czechfoolapp.domain.use_case.ValidateNumberOfPlayersUseCase
import com.example.czechfoolapp.domain.use_case.ValidateLosingScoreUseCase
import com.example.czechfoolapp.ui.gameoptionsroute.states.GameOptionsState
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class GameOptionsViewModel(
    private val validateNumberOfPlayersUseCase: ValidateNumberOfPlayersUseCase,
    private val validateLosingScoreUseCase: ValidateLosingScoreUseCase,
    private val currentGameRepository: CurrentGameRepository
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
        val numberOfPlayersResult = validateNumberOfPlayersUseCase(gameOptionsState.numberOfPlayersState.value.text)
        val losingScoreResult = validateLosingScoreUseCase(gameOptionsState.losingScoreState.value.text)

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
        saveGameToRepository()
        navigateToNext()
    }

    private fun saveGameToRepository() = viewModelScope.launch {
        val newGame = Game(
            losingScore = gameOptionsState.losingScoreState.value.text.toInt(),
            numberOfPlayers = gameOptionsState.losingScoreState.value.text.toInt(),
            date = LocalDateTime.now()
        )
        if (currentGameRepository.getCurrentGame() != null) {
            currentGameRepository.updateGame(newGame)
        } else {
            currentGameRepository.startGame(newGame)
        }
    }


    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CzechFoolApplication)
                val validateNumberOfPlayersUseCase = application.container.validateNumberOfPlayersUseCase
                val validateLosingScoreUseCase = application.container.validateLosingScoreUseCase
                val currentGameRepository = application.container.currentGameRepository
                GameOptionsViewModel(
                    validateNumberOfPlayersUseCase = validateNumberOfPlayersUseCase,
                    validateLosingScoreUseCase = validateLosingScoreUseCase,
                    currentGameRepository = currentGameRepository
                )
            }
        }
    }
}

