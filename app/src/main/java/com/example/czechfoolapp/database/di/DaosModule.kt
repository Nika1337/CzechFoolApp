package com.example.czechfoolapp.database.di

import com.example.czechfoolapp.database.CzechFoolGameDatabase
import com.example.czechfoolapp.database.dao.GameDao
import com.example.czechfoolapp.database.dao.PlayerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesGameDao(
        database: CzechFoolGameDatabase
    ): GameDao = database.gameDao()

    @Provides
    fun providesPlayerDao(
        database: CzechFoolGameDatabase
    ): PlayerDao = database.playerDao()
}