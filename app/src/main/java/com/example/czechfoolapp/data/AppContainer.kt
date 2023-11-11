package com.example.czechfoolapp.data

import android.content.Context
import com.example.czechfoolapp.data.repository.CurrentGameRepository
import com.example.czechfoolapp.data.repository.CurrentPlayerNamesRepository
import com.example.czechfoolapp.data.repository.DefaultCurrentGameRepository
import com.example.czechfoolapp.data.repository.DefaultCurrentPlayerNamesRepository
import com.example.czechfoolapp.data.repository.OfflineGamesRepository
import com.example.czechfoolapp.data.repository.OfflinePlayersRepository
import com.example.czechfoolapp.data.repository.PlayersRepository
import com.example.czechfoolapp.database.CzechFoolGameDatabase
import com.example.czechfoolapp.domain.use_case.PopulatePlayerNameStateUseCase
import com.example.czechfoolapp.domain.use_case.StartGameAndInsertPlayersUseCase
import com.example.czechfoolapp.domain.use_case.StoreCurrentPlayerNamesUseCase
import com.example.czechfoolapp.domain.use_case.validation.ValidateLosingScoreUseCase
import com.example.czechfoolapp.domain.use_case.validation.ValidateNumberOfPlayersUseCase
import com.example.czechfoolapp.domain.use_case.validation.ValidatePlayerNameUseCase

interface AppContainer {
    val validateLosingScoreUseCase: ValidateLosingScoreUseCase
    val validateNumberOfPlayersUseCase: ValidateNumberOfPlayersUseCase
    val validatePlayerNameUseCase: ValidatePlayerNameUseCase
    val currentGameRepository: CurrentGameRepository
    val currentPlayerNamesRepository: CurrentPlayerNamesRepository
    val populatePlayerNameStateUseCase: PopulatePlayerNameStateUseCase
    val startGameAndInsertPlayersUseCase: StartGameAndInsertPlayersUseCase
    val storeCurrentPlayerNamesUseCase: StoreCurrentPlayerNamesUseCase
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


    override val currentPlayerNamesRepository: CurrentPlayerNamesRepository by lazy {
        DefaultCurrentPlayerNamesRepository()
    }

    override val populatePlayerNameStateUseCase: PopulatePlayerNameStateUseCase by lazy {
        PopulatePlayerNameStateUseCase(
            currentGameRepository = currentGameRepository,
            currentPlayerNamesRepository = currentPlayerNamesRepository
        )
    }

    // For SetPlayersAndStartGameUseCase
    private val playerDao by lazy {
        CzechFoolGameDatabase.getDatabase(context).playerDao()
    }
    private val playersRepository: PlayersRepository by lazy {
        OfflinePlayersRepository(playerDao = playerDao)
    }
    override val startGameAndInsertPlayersUseCase: StartGameAndInsertPlayersUseCase by lazy {
        StartGameAndInsertPlayersUseCase(
            currentGameRepository = currentGameRepository,
            currentPlayerNamesRepository = currentPlayerNamesRepository,
            playersRepository = playersRepository
        )
    }
    override val storeCurrentPlayerNamesUseCase: StoreCurrentPlayerNamesUseCase by lazy {
        StoreCurrentPlayerNamesUseCase(
            currentPlayerNamesRepository = currentPlayerNamesRepository
        )
    }
}