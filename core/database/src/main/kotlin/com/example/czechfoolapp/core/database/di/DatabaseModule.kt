package com.example.czechfoolapp.core.database.di

import android.content.Context
import androidx.room.Room
import com.example.czechfoolapp.core.database.CzechFoolGameDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesCzechFoolDatabase(
        @ApplicationContext context: Context
    ): CzechFoolGameDatabase = Room.databaseBuilder(
        context,
        CzechFoolGameDatabase::class.java,
        "czech_fool_database"
    ).fallbackToDestructiveMigration().build()
}