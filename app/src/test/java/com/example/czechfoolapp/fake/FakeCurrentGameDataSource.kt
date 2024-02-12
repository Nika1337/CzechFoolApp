package com.example.czechfoolapp.fake

import com.example.czechfoolapp.datastore.CurrentGameData
import com.example.czechfoolapp.datastore.CurrentGameDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCurrentGameDataSource : CurrentGameDataSource {
    private var currentGameID = 0
    private var shouldEmit = true

    override fun getCurrentGameDataFlow(): Flow<CurrentGameData> =
        flow {
            while (true) {
                if (shouldEmit) {
                    emit(
                        CurrentGameData
                            .getDefaultInstance()
                            .toBuilder()
                            .setId(currentGameID)
                            .build()
                    )
                }
                shouldEmit = false
            }
        }


    override suspend fun setCurrentGameID(id: Int) {
        currentGameID = id
        shouldEmit = true
    }
}