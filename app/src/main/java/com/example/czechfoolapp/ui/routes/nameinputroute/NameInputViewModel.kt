package com.example.czechfoolapp.ui.routes.nameinputroute

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.czechfoolapp.CzechFoolApplication
import com.example.czechfoolapp.data.model.Game
import com.example.czechfoolapp.data.repository.CurrentGameManager
import com.example.czechfoolapp.domain.validation.ValidatePlayerNameUseCase
import com.example.czechfoolapp.ui.routes.nameinputroute.navigation.LOSING_SCORE_ARG
import com.example.czechfoolapp.ui.routes.nameinputroute.navigation.NUMBER_OF_PLAYERS_ARG
import com.example.czechfoolapp.ui.routes.nameinputroute.states.PlayerNameState
import com.example.czechfoolapp.util.capitalizeAndTrim
import com.example.czechfoolapp.util.getDuplicates
import com.example.czechfoolapp.util.resetNameErrors
import com.example.czechfoolapp.util.toPlayersList
import kotlinx.coroutines.launch

private const val PLAYER_NAMES_STATE = "playerNamesState"
class NameInputViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val validatePlayerNameUseCase: ValidatePlayerNameUseCase,
    private val currentGameManager: CurrentGameManager
) : ViewModel() {
    val playerNamesState =
        savedStateHandle.getStateFlow<Map<Int, PlayerNameState>>(
            key = PLAYER_NAMES_STATE,
            initialValue = emptyMap()
        )
    private val numberOfPlayers = savedStateHandle[NUMBER_OF_PLAYERS_ARG] ?: 0
    private val losingScore = savedStateHandle[LOSING_SCORE_ARG] ?: 0

    init {
        restoreOrSetPlayerNamesState()
    }

    private fun restoreOrSetPlayerNamesState() {
        val newPlayerNamesState = mutableMapOf<Int, PlayerNameState>()
        val currentPlayerNamesState = playerNamesState.value
        repeat(numberOfPlayers) {
            newPlayerNamesState[it + 1] = currentPlayerNamesState[it + 1] ?: PlayerNameState()
        }
        updatePlayerNameState(newPlayerNamesState)
    }

    fun onEvent(event: NameInputEvent) {
        when(event) {
            is NameInputEvent.PlayerNameChanged -> {
                changePlayerName(
                    id = event.id,
                    value = event.value
                )
            }
            is NameInputEvent.Next -> {
                submitPlayerNames(
                    navigateToNext = event.navigateToNext
                )
            }
        }
    }


    private fun changePlayerName(id: Int, value: String) {
        val newPlayerNamesState = playerNamesState.value.toMutableMap()
        newPlayerNamesState[id] = newPlayerNamesState[id].let {
            if (it == null) {
                Log.e("PlayerIDNull", id.toString())
                PlayerNameState()
            } else {
                it.copy(name = value)
            }
        }
        updatePlayerNameState(newPlayerNamesState)
    }

    private fun submitPlayerNames(navigateToNext: () -> Unit) {
        if (currentGameManager.isGameInProgress()) {
            return
        }
        val playerNamesValidationSuccess = validatePlayerNamesAndUpdateErrorMessages()
        if (playerNamesValidationSuccess.not()) {
            return
        }
        val game = Game.Builder()
            .losingScore(losingScore)
            .players(playerNamesState.value.toPlayersList())
            .build()
        viewModelScope.launch {
            currentGameManager.startNewGame(game)
            navigateToNext()
        }
    }

    private fun validatePlayerNamesAndUpdateErrorMessages(): Boolean {
        val areNamesUnique = validateNameUniquenessAndUpdateMessages()
        val areNamesStructureValid = validateNameStructureAndUpdateMessages()
        return areNamesUnique && areNamesStructureValid
    }

    private fun validateNameUniquenessAndUpdateMessages(): Boolean {
        val currentPlayerNamesState = playerNamesState.value.resetNameErrors()
        val capitalizedAndTrimmedState = currentPlayerNamesState.capitalizeAndTrim()
        val duplicateKeys = capitalizedAndTrimmedState.getDuplicates()
        val newPlayerNamesState = currentPlayerNamesState.toMutableMap()
        duplicateKeys.forEach { id ->
            newPlayerNamesState[id] =
                currentPlayerNamesState
                    .getOrDefault(id, PlayerNameState())
                    .copy(nameError = "Name Should Be Unique")
        }
        updatePlayerNameState(newPlayerNamesState)
        return duplicateKeys.isEmpty()
    }


    private fun validateNameStructureAndUpdateMessages(): Boolean {
        val currentPlayerNamesState = playerNamesState.value
        val newPlayerNamesState = currentPlayerNamesState.toMutableMap()
        var hasError = false
        currentPlayerNamesState.forEach { entry ->
            val validationResult = validatePlayerNameUseCase(entry.value.name)
            if (validationResult.successful.not()) {
                hasError = true
                newPlayerNamesState[entry.key] = entry.value.copy(nameError = validationResult.errorMessage)
            }
        }
        updatePlayerNameState(newPlayerNamesState)
        return hasError.not()
    }

    private fun updatePlayerNameState(newPlayerNamesState: Map<Int, PlayerNameState>) {
        savedStateHandle[PLAYER_NAMES_STATE] = newPlayerNamesState
    }
    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val savedStateHandle = this.createSavedStateHandle()
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CzechFoolApplication)
                val validatePlayerNameUseCase = application.container.validatePlayerNameUseCase
                val currentGameManager = application.container.currentGameManager
                NameInputViewModel(
                    savedStateHandle = savedStateHandle,
                    validatePlayerNameUseCase = validatePlayerNameUseCase,
                    currentGameManager = currentGameManager
                )
            }
        }
    }
}