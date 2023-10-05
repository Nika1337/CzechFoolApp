package com.example.czechfoolapp.ui.gameoptionsroute

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.czechfoolapp.domain.use_case.ValidateNumberOfPlayersUseCase
import com.example.czechfoolapp.domain.use_case.ValidateLosingScoreUseCase
import com.example.czechfoolapp.ui.gameoptionsroute.newstates.GameOptionsState

class GameOptionsViewModel(
    private val validateNumberOfPlayersUseCase: ValidateNumberOfPlayersUseCase, // TODO DI
    private val validateLosingScoreUseCase: ValidateLosingScoreUseCase // TODO DI
) : ViewModel() {
    private var gameOptionsState by mutableStateOf(GameOptionsState())

}

