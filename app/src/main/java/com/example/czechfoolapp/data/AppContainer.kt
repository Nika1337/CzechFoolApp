package com.example.czechfoolapp.data

import android.content.Context
import com.example.czechfoolapp.data.repository.CurrentGameRepository
import com.example.czechfoolapp.data.repository.CurrentPlayersRepository
import com.example.czechfoolapp.data.repository.DefaultCurrentGameRepository
import com.example.czechfoolapp.data.repository.DefaultCurrentPlayersRepository
import com.example.czechfoolapp.data.repository.OfflineGamesRepository
import com.example.czechfoolapp.data.repository.OfflinePlayersRepository
import com.example.czechfoolapp.data.repository.PlayersRepository
import com.example.czechfoolapp.database.CzechFoolGameDatabase
import com.example.czechfoolapp.domain.use_case.GetCurrentPlayerNamesUseCase
import com.example.czechfoolapp.domain.use_case.SetPlayersAndStartGameUseCase
import com.example.czechfoolapp.domain.use_case.validation.ValidateLosingScoreUseCase
import com.example.czechfoolapp.domain.use_case.validation.ValidateNumberOfPlayersUseCase
import com.example.czechfoolapp.domain.use_case.validation.ValidatePlayerNameUseCase

interface AppContainer {
    val validateLosingScoreUseCase: ValidateLosingScoreUseCase
    val validateNumberOfPlayersUseCase: ValidateNumberOfPlayersUseCase
    val validatePlayerNameUseCase: ValidatePlayerNameUseCase
    val currentGameRepository: CurrentGameRepository
    val currentPlayersRepository: CurrentPlayersRepository
    val getCurrentPlayerNamesUseCase: GetCurrentPlayerNamesUseCase
    val setPlayersAndStartGameUseCase: SetPlayersAndStartGameUseCase
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

    // For Current Game Repository
    private val gameDao by lazy {
        CzechFoolGameDatabase.getDatabase(context).gameDao()
    }
    private val gamesRepository by lazy {
        OfflineGamesRepository(gameDao = gameDao)
    }
    override val currentGameRepository: CurrentGameRepository by lazy {
        DefaultCurrentGameRepository(gamesRepository = gamesRepository)
    }

    // For Current Players Repository
    private val playerDao by lazy {
        CzechFoolGameDatabase.getDatabase(context).playerDao()
    }
    private val playersRepository: PlayersRepository by lazy {
        OfflinePlayersRepository(playerDao = playerDao)
    }
    override val currentPlayersRepository: CurrentPlayersRepository by lazy {
        DefaultCurrentPlayersRepository(playersRepository = playersRepository)
    }

    override val getCurrentPlayerNamesUseCase: GetCurrentPlayerNamesUseCase by lazy {
        GetCurrentPlayerNamesUseCase(
            currentGameRepository = currentGameRepository,
            currentPlayersRepository = currentPlayersRepository
        )
    }

    override val setPlayersAndStartGameUseCase: SetPlayersAndStartGameUseCase by lazy {
        SetPlayersAndStartGameUseCase(
            currentGameRepository = currentGameRepository,
            currentPlayersRepository = currentPlayersRepository
        )
    }
}