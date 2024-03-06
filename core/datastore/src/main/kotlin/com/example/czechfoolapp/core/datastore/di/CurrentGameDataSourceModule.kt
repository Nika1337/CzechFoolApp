package com.example.czechfoolapp.core.datastore.di

import com.example.czechfoolapp.core.datastore.CurrentGameDataSource
import com.example.czechfoolapp.core.datastore.DefaultCurrentGameDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class CurrentGameDataSourceModule {
    @Binds
    internal abstract fun bindsCurrentGameDataSource(
        currentGameDataSource: DefaultCurrentGameDataSource
    ): CurrentGameDataSource
}