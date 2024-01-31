package com.example.czechfoolapp.di

import android.content.Context
import com.example.czechfoolapp.data.DefaultValuesSource
import com.example.czechfoolapp.data.repository.CurrentGameManager
import com.example.czechfoolapp.data.repository.DefaultCardsRepository
import com.example.czechfoolapp.data.repository.DefaultCurrentGameManager
import com.example.czechfoolapp.data.repository.GamesRepository
import com.example.czechfoolapp.data.repository.OfflineGamesRepository
import com.example.czechfoolapp.data.repository.OfflinePlayersRepository
import com.example.czechfoolapp.data.repository.PlayersRepository
import com.example.czechfoolapp.database.CzechFoolGameDatabase
import com.example.czechfoolapp.domain.GetCardUIModelsUseCase
import com.example.czechfoolapp.domain.validation.ValidateLosingScoreUseCase
import com.example.czechfoolapp.domain.validation.ValidateNumberOfPlayersUseCase
import com.example.czechfoolapp.domain.validation.ValidatePlayerNameUseCase
import com.example.czechfoolapp.ui.gameoptionsroute.GameBuilder

interface AppContainer {
    val validateLosingScoreUseCase: ValidateLosingScoreUseCase
    val validateNumberOfPlayersUseCase: ValidateNumberOfPlayersUseCase
    val validatePlayerNameUseCase: ValidatePlayerNameUseCase
    val gameBuilder: GameBuilder
    val currentGameManager: CurrentGameManager
    val gamesRepository: GamesRepository
    val getCardUIModelsUseCase: GetCardUIModelsUseCase
    val defaultValuesSource: DefaultValuesSource
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

    private val playerDao by lazy {
        CzechFoolGameDatabase.getDatabase(context).playerDao()
    }
    private val playersRepository: PlayersRepository by lazy {
        OfflinePlayersRepository(playerDao = playerDao)
    }

    override val gameBuilder: GameBuilder by lazy {
        GameBuilder()
    }

    private val gameDao by lazy {
        CzechFoolGameDatabase.getDatabase(context).gameDao()
    }
    override val gamesRepository by lazy {
        OfflineGamesRepository(gameDao = gameDao)
    }

    private val defaultCardsRepository by lazy {
        DefaultCardsRepository()
    }
    override val getCardUIModelsUseCase: GetCardUIModelsUseCase by lazy {
        GetCardUIModelsUseCase(cardsRepository = defaultCardsRepository)
    }
    override val defaultValuesSource: DefaultValuesSource by lazy {
        DefaultValuesSource
    }

    override val currentGameManager: CurrentGameManager by lazy {
        DefaultCurrentGameManager(
            gamesRepository = gamesRepository,
            playersRepository = playersRepository
        )
    }
}