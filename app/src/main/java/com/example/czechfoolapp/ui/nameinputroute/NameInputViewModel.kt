package com.example.czechfoolapp.ui.nameinputroute

import android.util.Log
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class NameInputViewModel : ViewModel() {
    private val _playerNameState = mutableStateMapOf<Int, String>()
    val playerNameState = derivedStateOf { _playerNameState.toMap() }

    init {
        val repositoryMockFlow = flowOf(4)
        viewModelScope.launch {
            repositoryMockFlow.collect { playerNumber ->
                repeat(playerNumber) {
                    _playerNameState[it + 1] = ""
                }
            }
        }
        Log.d("playerNameState", playerNameState.value.toString())
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
        _playerNameState[id] = value

    }

    private fun submitPlayerNames(navigateToNext: () -> Unit) {
        navigateToNext()
        // TODO storing to repository
    }
}