package com.example.czechfoolapp.core.data.di

import com.example.czechfoolapp.core.data.repository.CardsRepository
import com.example.czechfoolapp.core.data.repository.CurrentGameManager
import com.example.czechfoolapp.core.data.repository.DefaultCardsRepository
import com.example.czechfoolapp.core.data.repository.DefaultCurrentGameManager
import com.example.czechfoolapp.core.data.repository.GamesRepository
import com.example.czechfoolapp.core.data.repository.OfflineGamesRepository
import com.example.czechfoolapp.core.data.repository.OfflinePlayersRepository
import com.example.czechfoolapp.core.data.repository.PlayersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsCardsRepository(
        cardsRepository: DefaultCardsRepository
    ): CardsRepository

    @Binds
    @Singleton
    internal abstract fun bindsCurrentGameManager(
        currentGameManager: DefaultCurrentGameManager
    ): CurrentGameManager

    @Binds
    internal abstract fun bindsGamesRepository(
        gamesRepository: OfflineGamesRepository
    ): GamesRepository

    @Binds
    internal abstract fun bindsPlayersRepository(
        playersRepository: OfflinePlayersRepository
    ): PlayersRepository
}





