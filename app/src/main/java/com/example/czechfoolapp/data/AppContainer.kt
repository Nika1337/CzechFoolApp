package com.example.czechfoolapp.data

import android.content.Context
import com.example.czechfoolapp.data.repository.CurrentGameRepository
import com.example.czechfoolapp.data.repository.DefaultCurrentGameRepository
import com.example.czechfoolapp.data.repository.OfflineGamesRepository
import com.example.czechfoolapp.database.CzechFoolGameDatabase
import com.example.czechfoolapp.domain.use_case.ValidateLosingScoreUseCase
import com.example.czechfoolapp.domain.use_case.ValidateNumberOfPlayersUseCase
import com.example.czechfoolapp.domain.use_case.ValidatePlayerNameUseCase

interface AppContainer {
    val validateLosingScoreUseCase: ValidateLosingScoreUseCase
    val validateNumberOfPlayersUseCase: ValidateNumberOfPlayersUseCase
    val validatePlayerNameUseCase: ValidatePlayerNameUseCase
    val currentGameRepository: CurrentGameRepository
}

class DefaultAppContainer(
    private val context: Context
) : AppContainer {
    override val validateLosingScoreUseCase: ValidateLosingScoreUseCase by lazy {
        ValidateLosingScoreUseCase()
    }

    override val validateNumberOfPlayersUseCase: ValidateNumberOfPlayersUseCase by lazy {
        ValidateNumberOfPlayersUseCase()
    }

    override val validatePlayerNameUseCase: ValidatePlayerNameUseCase by lazy {
        ValidatePlayerNameUseCase()
    }

    private val gameDao by lazy {
        CzechFoolGameDatabase.getDatabase(context).gameDao()
    }
    private val gamesRepository by lazy {
        OfflineGamesRepository(gameDao = gameDao)
    }
    override val currentGameRepository: CurrentGameRepository by lazy {
        DefaultCurrentGameRepository(gamesRepository = gamesRepository)
    }
}