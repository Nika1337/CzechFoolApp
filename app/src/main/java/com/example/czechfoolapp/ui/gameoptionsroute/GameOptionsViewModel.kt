package com.example.czechfoolapp.ui.gameoptionsroute

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.czechfoolapp.ui.gameoptionsroute.newstates.GameOptionsState

class GameOptionsViewModel : ViewModel() {
    private var gameOptionsState by mutableStateOf(GameOptionsState())

}

