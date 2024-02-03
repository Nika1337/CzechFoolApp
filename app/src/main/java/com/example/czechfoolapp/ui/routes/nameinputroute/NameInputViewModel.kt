package com.example.czechfoolapp.ui.routes.nameinputroute

import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateMapOf
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

class NameInputViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val validatePlayerNameUseCase: ValidatePlayerNameUseCase,
    private val currentGameManager: CurrentGameManager
) : ViewModel() {
    private val _playerNameState = mutableStateMapOf<Int, PlayerNameState>()
    val playerNameState = derivedStateOf { _playerNameState.toMap() }
    private val numberOfPlayers = savedStateHandle[NUMBER_OF_PLAYERS_ARG] ?: 0
    private val losingScore = savedStateHandle[LOSING_SCORE_ARG] ?: 0

    init {
        repeat(numberOfPlayers) {
            _playerNameState[it + 1] =
                PlayerNameState(
                    name = "" // TODO persisting
                )
        }
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
        _playerNameState[id] = _playerNameState[id].let {
            if (it == null) {
                Log.e("PlayerIDNull", id.toString())
                PlayerNameState()
            } else {
                it.copy(name = value)
            }
        }
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
                .players(_playerNameState.toPlayersList())
                .build()
            currentGameManager.startNewGame(game)
            navigateToNext()
        }
    }

    private fun validatePlayerNamesAndUpdateErrorMessages() : Boolean {
        var hasError = validateNameStructureAndUpdateMessages()
        hasError = hasError || validateNameUniqueness()
        return hasError.not()
    }

    private fun validateNameUniqueness(): Boolean {
        val duplicateKeys = _playerNameState.toMap().getDuplicates()
        duplicateKeys.forEach {
            _playerNameState[it] = _playerNameState.getOrDefault(it, PlayerNameState()).copy(
                nameError = "Name should be unique"
            )
        }
        return duplicateKeys.isNotEmpty()
    }

    private fun validateNameStructureAndUpdateMessages(): Boolean {
        var hasError = false
        _playerNameState.forEach { entry ->
            val validationResult = validatePlayerNameUseCase(entry.value.name)
            if (validationResult.successful.not()) {
                hasError = true
                _playerNameState[entry.key] =
                    entry.value.copy(nameError = validationResult.errorMessage)
            } else {
                _playerNameState[entry.key] =
                    entry.value.copy(nameError = validationResult.errorMessage)
            }
        }
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