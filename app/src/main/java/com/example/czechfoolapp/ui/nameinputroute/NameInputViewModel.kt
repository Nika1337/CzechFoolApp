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
import com.example.czechfoolapp.domain.use_case.PopulatePlayerNameStateUseCase
import com.example.czechfoolapp.domain.use_case.StoreCurrentPlayerNamesUseCase
import com.example.czechfoolapp.domain.use_case.StartGameAndInsertPlayersUseCase
import com.example.czechfoolapp.domain.use_case.validation.ValidatePlayerNameUseCase
import com.example.czechfoolapp.util.getDuplicates
import kotlinx.coroutines.launch

class NameInputViewModel(
    private val validatePlayerNameUseCase: ValidatePlayerNameUseCase,
    private val populatePlayerNameStateUseCase: PopulatePlayerNameStateUseCase,
    private val startGameAndInsertPlayersUseCase: StartGameAndInsertPlayersUseCase,
    private val storeCurrentPlayerNamesUseCase: StoreCurrentPlayerNamesUseCase
) : ViewModel() {
    private val _playerNameState = mutableStateMapOf<Int, PlayerNameState>()
    val playerNameState = derivedStateOf { _playerNameState.toMap() }

    init {
        populatePlayerNameStateUseCase(_playerNameState)
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
            is NameInputEvent.Back -> {
                storeCurrentPlayerNames(event.navigateUp)
            }
        }
    }

    private fun storeCurrentPlayerNames(navigateUp: () -> Unit) {
        storeCurrentPlayerNamesUseCase(_playerNameState.toMap())
        navigateUp()
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
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CzechFoolApplication)
                val validatePlayerNameUseCase = application.container.validatePlayerNameUseCase
                val getCurrentPlayerNamesUseCase = application.container.populatePlayerNameStateUseCase
                val setPlayersAndStartGameUseCase = application.container.startGameAndInsertPlayersUseCase
                val storeCurrentPlayerNamesUseCase = application.container.storeCurrentPlayerNamesUseCase
                NameInputViewModel(
                    validatePlayerNameUseCase = validatePlayerNameUseCase,
                    populatePlayerNameStateUseCase = getCurrentPlayerNamesUseCase,
                    startGameAndInsertPlayersUseCase = setPlayersAndStartGameUseCase,
                    storeCurrentPlayerNamesUseCase = storeCurrentPlayerNamesUseCase
                )
            }
        }
    }
}