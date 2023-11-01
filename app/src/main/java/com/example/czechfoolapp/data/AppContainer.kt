package com.example.czechfoolapp.data

import android.content.Context
import com.example.czechfoolapp.domain.use_case.ValidateLosingScoreUseCase
import com.example.czechfoolapp.domain.use_case.ValidateNumberOfPlayersUseCase
import com.example.czechfoolapp.domain.use_case.ValidatePlayerNameUseCase

interface AppContainer {
    val validateLosingScoreUseCase: ValidateLosingScoreUseCase
    val validateNumberOfPlayersUseCase: ValidateNumberOfPlayersUseCase
    val validatePlayerNameUseCase: ValidatePlayerNameUseCase
}

class DefaultAppContainer(
    private val context: Context
) : AppContainer {
    override val validateLosingScoreUseCase: ValidateLosingScoreUseCase = ValidateLosingScoreUseCase()
    override val validateNumberOfPlayersUseCase: ValidateNumberOfPlayersUseCase = ValidateNumberOfPlayersUseCase()
    override val validatePlayerNameUseCase: ValidatePlayerNameUseCase = ValidatePlayerNameUseCase()
}