package com.example.czechfoolapp.feature.nameinput

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.czechfoolapp.core.data.repository.CurrentGameManager
import com.example.czechfoolapp.core.domain.validation.ValidatePlayerNameUseCase
import com.example.czechfoolapp.core.model.Game
import com.example.czechfoolapp.core.model.Player
import com.example.czechfoolapp.feature.nameinput.navigation.LOSING_SCORE_ARG
import com.example.czechfoolapp.feature.nameinput.navigation.NUMBER_OF_PLAYERS_ARG
import com.example.czechfoolapp.feature.nameinput.states.PlayerNameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PLAYER_NAMES_STATE = "playerNamesState"

@HiltViewModel
class NameInputViewModel @Inject constructor(
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
}

private fun Map<Int, PlayerNameState>.toPlayersList() =
    this.toList()
        .map { (id: Int, playerNameState: PlayerNameState) ->
            Player(
                id = id,
                name = playerNameState.name.trim()
            )
        }

