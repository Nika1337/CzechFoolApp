package com.example.czechfoolapp.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.example.czechfoolapp.core.datastore.CurrentGameData
import com.example.czechfoolapp.core.datastore.currentGameDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    internal fun providesCurrentGameDataDataStore(
        @ApplicationContext context: Context,
    ): DataStore<CurrentGameData> = context.currentGameDataStore

}