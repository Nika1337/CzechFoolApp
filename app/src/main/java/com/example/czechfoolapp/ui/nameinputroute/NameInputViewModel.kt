package com.example.czechfoolapp.ui.nameinputroute

import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.czechfoolapp.CzechFoolApplication
import com.example.czechfoolapp.domain.use_case.GetCurrentPlayerNamesUseCase
import com.example.czechfoolapp.domain.use_case.StartGameAndInsertPlayersUseCase
import com.example.czechfoolapp.domain.use_case.validation.ValidatePlayerNameUseCase
import kotlinx.coroutines.launch

class NameInputViewModel(
    private val validatePlayerNameUseCase: ValidatePlayerNameUseCase,
    private val getCurrentPlayerNamesUseCase: GetCurrentPlayerNamesUseCase,
    private val startGameAndInsertPlayersUseCase: StartGameAndInsertPlayersUseCase
) : ViewModel() {
    private val _playerNameState = mutableStateMapOf<Int, PlayerNameState>()
    val playerNameState = derivedStateOf { _playerNameState.toMap() }

    init {
        populatePlayerNameState()
    }

    private fun populatePlayerNameState() {
        _playerNameState.clear()
        getCurrentPlayerNamesUseCase().forEach {(id: Int, playerNameState: PlayerNameState) ->
            _playerNameState[id] = playerNameState
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
        viewModelScope.launch {
            val playerNamesValidationSuccess = validatePlayerNamesAndUpdateErrorMessages()
            if (playerNamesValidationSuccess.not()) {
                return@launch
            }
            startGameAndInsertPlayersUseCase(_playerNameState)
            navigateToNext()
        }
    }

    private fun validatePlayerNamesAndUpdateErrorMessages() : Boolean {
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
        return hasError.not()
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CzechFoolApplication)
                val validatePlayerNameUseCase = application.container.validatePlayerNameUseCase
                val getCurrentPlayerNamesUseCase = application.container.getCurrentPlayerNamesUseCase
                val setPlayersAndStartGameUseCase = application.container.startGameAndInsertPlayersUseCase
                NameInputViewModel(
                    validatePlayerNameUseCase = validatePlayerNameUseCase,
                    getCurrentPlayerNamesUseCase = getCurrentPlayerNamesUseCase,
                    startGameAndInsertPlayersUseCase = setPlayersAndStartGameUseCase
                )
            }
        }
    }
}