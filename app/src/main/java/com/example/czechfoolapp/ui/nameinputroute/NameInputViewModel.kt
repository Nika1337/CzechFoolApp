package com.example.czechfoolapp.ui.nameinputroute

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel

class NameInputViewModel : ViewModel() {
    private val _playerNameState = mutableStateMapOf(1 to "", 2 to "")
    val playerNameState = _playerNameState.toMap()
    private fun changePlayerNameState(id: Int, value: String) {
        _playerNameState[id] = value
    }
}