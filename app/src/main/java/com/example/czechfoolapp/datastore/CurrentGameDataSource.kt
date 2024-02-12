package com.example.czechfoolapp.datastore

import kotlinx.coroutines.flow.Flow

interface CurrentGameDataSource {
    fun getCurrentGameDataFlow(): Flow<CurrentGameData>
    suspend fun setCurrentGameID(id: Int)
}