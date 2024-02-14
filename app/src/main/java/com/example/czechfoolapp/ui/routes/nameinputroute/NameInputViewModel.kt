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
import com.example.czechfoolapp.util.getDuplicates
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
        val newPlayersNamesState = mutableMapOf<Int, PlayerNameState>()
        val currentPlayerNamesState = playerNamesState.value
        repeat(numberOfPlayers) {
            newPlayersNamesState[it + 1] = currentPlayerNamesState[it + 1] ?: PlayerNameState()
        }
        savedStateHandle[PLAYER_NAMES_STATE] = newPlayersNamesState
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
        val newPlayerNameState = playerNamesState.value.toMutableMap()
        newPlayerNameState[id] = newPlayerNameState[id].let {
            if (it == null) {
                Log.e("PlayerIDNull", id.toString())
                PlayerNameState()
            } else {
                it.copy(name = value)
            }
        }
        savedStateHandle[PLAYER_NAMES_STATE] = newPlayerNameState
    }

    private fun submitPlayerNames(navigateToNext: () -> Unit) {
        if (currentGameManager.isGameInProgress()) {
            return
        }
        viewModelScope.launch {
            val playerNamesValidationSuccess = validatePlayerNamesAndUpdateErrorMessages()
            if (playerNamesValidationSuccess.not()) {
                return@launch
            }
            val game = Game.Builder()
                .numberOfPlayers(numberOfPlayers)
                .losingScore(losingScore)
                .players(playerNamesState.value.toPlayersList())
                .build()
            currentGameManager.startNewGame(game)
            navigateToNext()
        }
    }

    private fun validatePlayerNamesAndUpdateErrorMessages() : Boolean {
        val hasError = validateNameStructureAndUpdateMessages() || validateNameUniqueness()
        return hasError.not()
    }

    private fun validateNameUniqueness(): Boolean {
        val currentPlayerNamesState = playerNamesState.value
        val duplicateKeys = currentPlayerNamesState.getDuplicates()
        val newPlayerNamesState = mutableMapOf<Int, PlayerNameState>()
        duplicateKeys.forEach {
           newPlayerNamesState[it] = currentPlayerNamesState.getOrDefault(it, PlayerNameState()).copy(
                nameError = "Name should be unique"
            )
        }
        return duplicateKeys.isNotEmpty()
    }

    private fun validateNameStructureAndUpdateMessages(): Boolean {
        var hasError = false
        val currentPlayerNamesState = playerNamesState.value
        val newPlayerNamesState = mutableMapOf<Int, PlayerNameState>()
        currentPlayerNamesState.forEach { entry ->
            val validationResult = validatePlayerNameUseCase(entry.value.name)
            if (validationResult.successful.not()) {
                hasError = true
                newPlayerNamesState[entry.key] =
                    entry.value.copy(nameError = validationResult.errorMessage)
            } else {
                newPlayerNamesState[entry.key] =
                    entry.value.copy(nameError = validationResult.errorMessage)
            }
        }
        savedStateHandle[PLAYER_NAMES_STATE] = newPlayerNamesState
        return hasError
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