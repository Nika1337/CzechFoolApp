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
import com.example.czechfoolapp.domain.use_case.ValidatePlayerNameUseCase
import com.example.czechfoolapp.ui.gameoptionsroute.GameOptionsViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class NameInputViewModel(
    private val validatePlayerNameUseCase: ValidatePlayerNameUseCase
) : ViewModel() {
    private val _playerNameState = mutableStateMapOf<Int, PlayerNameState>()
    val playerNameState = derivedStateOf { _playerNameState.toMap() }

    init {
        val repositoryMockFlow = flowOf(4)
        viewModelScope.launch {
            repositoryMockFlow.collect { playerNumber ->
                repeat(playerNumber) {
                    _playerNameState[it + 1] = PlayerNameState()
                }
            }
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
        var hasError = false
        _playerNameState.forEach { entry ->
            val validationResult = validatePlayerNameUseCase(entry.value.name)
            if (validationResult.successful.not()) {
                _playerNameState[entry.key] = entry.value.copy(nameError = validationResult.errorMessage)
                hasError = true
            } else {
                _playerNameState[entry.key] = entry.value.copy(nameError = validationResult.errorMessage)
            }
        }
        if (hasError) {
            return
        }
        navigateToNext()
        // TODO storing to repository
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CzechFoolApplication)
                val validatePlayerNameUseCase = application.container.validatePlayerNameUseCase
                NameInputViewModel(
                    validatePlayerNameUseCase = validatePlayerNameUseCase
                )
            }
        }
    }
}