package com.example.czechfoolapp.datastore.di

import com.example.czechfoolapp.datastore.CurrentGameDataSource
import com.example.czechfoolapp.datastore.DefaultCurrentGameDataSource
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